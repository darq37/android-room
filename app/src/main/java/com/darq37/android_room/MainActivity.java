package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.darq37.android_room.activities.login.ui.login.LoginActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.room.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private AppDatabase appDatabase;
    private Button button;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        appDatabase = RoomConstant.getInstance(this);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnLogin);
    }

    public void goToLoginAtivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}