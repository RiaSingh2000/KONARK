package com.konarktimes.konark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=getSharedPreferences("skip",MODE_PRIVATE);
        //int lang_id=sharedPreferences.getInt("lang_id",-1);
        boolean check=sharedPreferences.getBoolean("activity_executed",false);
        if(!check){
            startActivity(new Intent(MainActivity.this,Wizard.class));
        }
        else{
            startActivity(new Intent(MainActivity.this,SplashScreen.class));
        }
        MainActivity.this.finish();

    }
}
