package com.example.myapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    EditText etCode;
    Button btnPost;
    String code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etCode = (EditText) findViewById(R.id.code);
        btnPost = (Button) findViewById(R.id.btnRegister);
    }

    private boolean validate(){
        code = etCode.getText().toString().trim();
        if(!code.equals(""))
            return true;
        else
            return false;
    }

    public void clickButton(View view){
        if(!validate())
        {
            Toast.makeText(RegisterActivity.this, "Enter the code!", Toast.LENGTH_LONG).show();
        }
        else
        {
            HttpAsyncTask task = new HttpAsyncTask();
            task.execute(ApiHelper.TOKEN_URL);
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(RegisterActivity.this, "",
                    "Loading, please wait...", true);
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            try
            {
                ApiHelper api = new ApiHelper();
                result = api.getToken(GlobalVar.Phone, code).getString("token");
                Log.d("[RegisterActivity]", "Result: " + result);
                if(result != ""){
                    GlobalVar.Token = result;
                }
            }
            catch (Exception ex)
            {
                return "";
               // Toast.makeText(RegisterActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            dialog.dismiss();
            if(!result.equals(""))
            {
                Log.i("[RegisterActivity]", "Starting sp: ");
                SharedPreferences sp = getSharedPreferences(GlobalVar.MOZAN, 0);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString(GlobalVar.MOZAN_PHONE, GlobalVar.Phone);
                Ed.putString(GlobalVar.MOZAN_TOKEN,GlobalVar.Token);
                Ed.commit();
                Log.i("[RegisterActivity]", "Ending sp: ");
                Intent in = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(in);
            }
        }
    }
}