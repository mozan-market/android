package com.example.mozan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 10.12.2014.
 */
public class RegisterActivity extends Activity {

    private EditText code = null;
    registerTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        code = (EditText)findViewById(R.id.code);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void register(View view){
        task = new registerTask();
        //registerTask.execute();
    }

    class registerTask extends AsyncTask<String, Void, ServerResponse> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(RegisterActivity.this, "",
                    "Loading, please wait...", true);
        }

        @Override
        protected ServerResponse doInBackground(String... phone) {
            try {
                JSONObject response = ApiHelper.getCode(phone[0]);
                return new ServerResponse(response.getBoolean("error"),
                        response.getString("message"), response);
            } catch (ApiHelper.ApiException e) {
                Log.e(ApiHelper.TAG, e.getMessage());
            } catch (JSONException e) {
                Log.e(ApiHelper.TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ServerResponse serverResponse) {
            super.onPostExecute(serverResponse);

            dialog.dismiss();

            if (serverResponse == null) {
                Toast.makeText(RegisterActivity.this, "Error ocupied, try later",
                        Toast.LENGTH_SHORT).show();
            } else if (serverResponse.hasError()) {
                Toast.makeText(RegisterActivity.this, serverResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(RegisterActivity.this,
                        HomeActivity.class));
            }
        }
    }
}