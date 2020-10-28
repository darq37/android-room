package com.darq37.android_room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.activities.product.ProductActivity;
import com.darq37.android_room.adapters.ShoppingListAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    public User loggedInUser;
    private UserDao userDao;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();
        userDao = RoomConstant.getInstance(this).userDao();

        Button logout = findViewById(R.id.logOutButton);
        FloatingActionButton settingsButton = findViewById(R.id.settingsButton);
        FloatingActionButton addNewListButton = findViewById(R.id.to_new_list_button);
        FloatingActionButton addProductButton = findViewById(R.id.to_products_button);


        TextView welcomeView = findViewById(R.id.welcome);
        RecyclerView recyclerView = findViewById(R.id.shoppingLists);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", null);

        loggedInUser = userDao.getByIdSync(user);
        String displayName = loggedInUser.getDisplayName();

        String welcomeString = String.format(res.getString(R.string.welcomeString), displayName);
        welcomeView.setText(welcomeString);

        shoppingListAdapter = new ShoppingListAdapter(RoomConstant.getInstance(this)
                .shoppingListDao()
                .getAllForUserSync(loggedInUser.getLogin()));
        recyclerView.setAdapter(shoppingListAdapter);

        logout.setOnClickListener(this::logout);
        settingsButton.setOnClickListener(this::toAccountActivity);
        addNewListButton.setOnClickListener(this::toListActivity);
        addProductButton.setOnClickListener(this::toProductActivity);
    }


    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        loggedInUser = null;
        deleteSharedPreferences("app");
        startActivity(intent);
    }

    public void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void toProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

}