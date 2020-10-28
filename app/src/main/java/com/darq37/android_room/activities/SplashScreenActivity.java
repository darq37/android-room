package com.darq37.android_room.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.login.LoginActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        }, SPLASH_TIME_OUT);
    }

}