package com.knby.notesy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To make the activity fullscreen
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Making a handler to stop the activity after 1.5 seconds

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Starts the main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                // Closes this activity
                finish();
            }
        }, 1500);

        setContentView(R.layout.activity_splash_screen);
    }
}
