package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.login.data.model.LoggedInUser;
import com.darq37.android_room.activities.login.ui.login.LoginActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.room.AppDatabase;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private AppDatabase appDatabase;
    private LoggedInUser loggedInUser;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        appDatabase = RoomConstant.getInstance(this);
        setContentView(R.layout.activity_main);

        Button logOut = findViewById(R.id.logOutButton);
        Button settings =  findViewById(R.id.settingsButton);
        Button share =  findViewById(R.id.shareButton);
        String welcome = "Welcome " + loggedInUser.getDisplayName();




        logOut.setOnClickListener(this::toLoginActivity);
    }

    public void toLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        //TODO Implement logout
        startActivity(intent);
    }

    public void toAccountActivity(View view){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }







    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}