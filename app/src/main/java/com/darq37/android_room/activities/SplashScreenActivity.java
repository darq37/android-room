package com.darq37.android_room.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.room.AppDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 2000;
    private static Context context;
    private static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = getApplicationContext();
        appDatabase = RoomConstant.getInstance(context);
        

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        }, SPLASH_TIME_OUT);
    }


    public static Context getContext() {
        return context;
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}