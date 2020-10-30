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
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private ShoppingListAdapter shoppingListAdapter;
    private ShoppingListDao shoppingListDao;
    private Button logout;
    private FloatingActionButton settingsButton;
    private FloatingActionButton addNewListButton;
    private FloatingActionButton addProductButton;
    private TextView welcomeView;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeViews();

        Resources res = getResources();
        UserDao userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        shoppingListAdapter = new ShoppingListAdapter(new ArrayList<>());


        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        String userName = sharedPreferences.getString("user", null);

        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(user -> {
                    String displayName = user.getDisplayName();
                    String welcomeString = String.format(res.getString(R.string.welcomeString), displayName);
                    welcomeView.setText(welcomeString);
                })
                .subscribe();

        shoppingListDao.getAllForUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(list -> {
                    shoppingListAdapter.setLists(list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(shoppingListAdapter);
                })
                .subscribe();

        logout.setOnClickListener(this::logout);
        settingsButton.setOnClickListener(this::toAccountActivity);
        addNewListButton.setOnClickListener(this::toListActivity);
        addProductButton.setOnClickListener(this::toProductActivity);
    }

    private void initializeViews() {
        logout = findViewById(R.id.logOutButton);
        settingsButton = findViewById(R.id.settingsButton);
        addNewListButton = findViewById(R.id.to_new_list_button);
        addProductButton = findViewById(R.id.to_products_button);
        welcomeView = findViewById(R.id.welcome);
        recyclerView = findViewById(R.id.shoppingLists);
    }


    @Override
    protected void onResume() {
        shoppingListDao.getAllForUser(sharedPreferences.getString("user", null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(list -> shoppingListAdapter.setLists(list))
                .subscribe();
        super.onResume();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
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