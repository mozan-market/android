package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity {

    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btnLogout = (Button) findViewById(R.id.btnLogout);
    }

    public void clickButton(View view){
        try
        {
            SharedPreferences sp = this.getSharedPreferences(GlobalVar.MOZAN,0);
            SharedPreferences.Editor Ed = sp.edit();
            Ed.putString(GlobalVar.MOZAN_PHONE, "");
            Ed.putString(GlobalVar.MOZAN_TOKEN, "");
            Ed.commit();
            Intent in = new Intent(HomeActivity.this, CodeActivity.class);
            startActivity(in);
        }
        catch (Exception ex)
        {
            Toast.makeText(HomeActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}