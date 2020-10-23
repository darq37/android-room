package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.activities.list.ShoppingListAdapter;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.activities.shared.SharedActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.function.BiConsumer;


public class MainActivity extends AppCompatActivity {
    private User loggedInUser;
    private UserDao userDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();
        userDao = RoomConstant.getInstance(this).userDao();

        Button logout = findViewById(R.id.logOutButton);
        FloatingActionButton settings = findViewById(R.id.settingsButton);
        FloatingActionButton share = findViewById(R.id.shareButton);
        FloatingActionButton lists = findViewById(R.id.goToListsButton);

        TextView welcomeView = findViewById(R.id.welcome);

        RecyclerView recyclerView = findViewById(R.id.shoppingLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(RoomConstant.getInstance(this).shoppingListDao().getAllSync());
        recyclerView.setAdapter(shoppingListAdapter);



        Intent intent = getIntent();
        loggedInUser = userDao.getByIdSync(intent.getStringExtra("LOGIN"));
        String displayName = loggedInUser.getDisplayName();
        intent.putExtra("username", displayName);

        String welcomeString = String.format(res.getString(R.string.welcomeString), displayName);
        welcomeView.setText(welcomeString);

        logout.setOnClickListener(this::logout);
        settings.setOnClickListener(this::toAccountActivity);
        share.setOnClickListener(this::toShareActivity);
        lists.setOnClickListener(this::toListActivity);

    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        loggedInUser = null;
        startActivity(intent);
    }

    public void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtras(this.getIntent());
        startActivity(intent);
    }

    public void toShareActivity(View view) {
        Intent intent = new Intent(this, SharedActivity.class);
        intent.putExtras(this.getIntent());
        startActivity(intent);
    }

    public void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtras(this.getIntent());
        startActivity(intent);
    }


}