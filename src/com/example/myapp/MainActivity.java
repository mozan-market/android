package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by User on 10.12.2014.
 */
public class MainActivity extends Activity {

    SharedPreferences sp;
    String phone;
    String token;
    Intent in;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sp = this.getSharedPreferences(GlobalVar.MOZAN,0);
        phone = sp.getString(GlobalVar.MOZAN_PHONE, "");
        token = sp.getString(GlobalVar.MOZAN_TOKEN, "");

        Log.d("MainActivity", "Phone/token: " + phone+ " / "+ token);
        if (!phone.equals("") && !token.equals(""))
        {
            GlobalVar.Phone = phone;
            GlobalVar.Token = token;
            in = new Intent(this,
                    HomeActivity.class);
            startActivity(in);
        }
        else
        {
            in = new Intent(this, CodeActivity.class);
            startActivity(in);
        }
    }
}