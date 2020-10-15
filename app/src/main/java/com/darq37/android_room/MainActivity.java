package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.room.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private AppDatabase appDatabase;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        appDatabase = RoomConstant.getInstance(this);
        setContentView(R.layout.activity_main);
    }


    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}