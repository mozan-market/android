package com.example.mozan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class GetCodeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private EditText  phone = null;
    getCodeTask codeTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getcode);
        phone = (EditText)findViewById(R.id.phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getcode(View view){
        codeTask = new getCodeTask();
        codeTask.execute();
    }

    class getCodeTask extends AsyncTask<String, Void, ServerResponse> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(GetCodeActivity.this, "",
                    "Loading, please wait...", true);
        }

        @Override
        protected ServerResponse doInBackground(String... phone) {
            try {
                Global.phone = phone[0];
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
                Toast.makeText(GetCodeActivity.this, "Error ocupied, try later",
                        Toast.LENGTH_SHORT).show();
            } else if (serverResponse.hasError()) {
                Toast.makeText(GetCodeActivity.this, serverResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
            } else {

                Global.code = serverResponse.toString();
                Intent intent = new Intent(GetCodeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }
    }
}
