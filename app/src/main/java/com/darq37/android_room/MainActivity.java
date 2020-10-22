package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.activities.list.ShoppingListAdapter;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.activities.shared.SharedActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.darq37.android_room.activities.SplashScreenActivity.getAppDatabase;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button logOut = findViewById(R.id.logOutButton);
        FloatingActionButton settings = findViewById(R.id.settingsButton);
        FloatingActionButton share = findViewById(R.id.shareButton);
        FloatingActionButton lists = findViewById(R.id.goToListsButton);

        TextView welcomeView = findViewById(R.id.welcome);

        RecyclerView recyclerView = findViewById(R.id.shoppingLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getAppDatabase().shoppingListDao().getAllSync());
        recyclerView.setAdapter(shoppingListAdapter);


        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.userNAME);
        welcomeView.setText("Welcome".concat(message));
        logOut.setOnClickListener(this::toLoginActivity);
        settings.setOnClickListener(this::toAccountActivity);
        share.setOnClickListener(this::toShareActivity);
        lists.setOnClickListener(this::toListActivity);

    }

    public void toLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        //TODO Implement logout
        startActivity(intent);
    }

    public void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void toShareActivity(View view) {
        Intent intent = new Intent(this, SharedActivity.class);
        startActivity(intent);
    }

    public void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }


}