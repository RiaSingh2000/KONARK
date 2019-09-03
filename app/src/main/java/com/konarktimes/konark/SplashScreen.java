package com.konarktimes.konark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    //ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

      // logo=findViewById(R.id.logo);
        //Glide.with(SplashScreen.this).load("http://www.deviprasadnayak.com/konark/uploads/logo/logo_5d46768d4d38b2.png").into(logo);

        LogoLauncher logoLauncher=new LogoLauncher();
        logoLauncher.start();
        //logo.setImageResource(R.drawable.logo);



    }
    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SplashScreen.this, HomeActivity.class));
            SplashScreen.this.finish();

        }
    }
}
