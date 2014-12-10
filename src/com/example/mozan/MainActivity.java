package com.example.mozan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by User on 10.12.2014.
 */
public class MainActivity extends Activity {

    SharedPreferences sp;
    String phone;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = this.getSharedPreferences("Mozan",0);
        phone = sp.getString("mozan_phone", null);
        token = sp.getString("mozan_token", null);
        Global.prepareUserAgent(this);

        //finish();
        if (phone != "" && token != "")
        {
            Global.phone = phone;
            Global.token = token;
            Intent intnt = new Intent(this,
                    HomeActivity.class);
            startActivity(intnt);
        }
        else
        {
            Intent intnt = new Intent(this,
                    GetCodeActivity.class);
            startActivity(intnt);
        }
    }
}