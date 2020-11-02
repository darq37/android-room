package com.darq37.android_room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private ShoppingListAdapter shoppingListAdapter;
    private ShoppingListDao shoppingListDao;
    private Button logout;
    private ImageButton sortByAZButton;
    private ImageButton sortByIdButton;
    private FloatingActionButton settingsButton;
    private FloatingActionButton addNewListButton;
    private FloatingActionButton addProductButton;
    private TextView welcomeView;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private UserDao userDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeViews();

        Resources res = getResources();

        userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        shoppingListAdapter = new ShoppingListAdapter(Collections.emptyList());


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
                .flatMap(user -> shoppingListDao
                        .getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(result -> {
                            shoppingListAdapter.setLists(result);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(shoppingListAdapter);
                        }))
                .subscribe();

        logout.setOnClickListener(this::logout);
        settingsButton.setOnClickListener(this::toAccountActivity);
        addNewListButton.setOnClickListener(this::toListActivity);
        addProductButton.setOnClickListener(this::toProductActivity);
        sortByIdButton.setOnClickListener(this::sortById);
        sortByAZButton.setOnClickListener(this::sortByAZ);

    }

    private void initializeViews() {
        logout = findViewById(R.id.logOutButton);
        settingsButton = findViewById(R.id.settingsButton);
        addNewListButton = findViewById(R.id.to_new_list_button);
        addProductButton = findViewById(R.id.to_products_button);
        sortByAZButton = findViewById(R.id.sortByAlphaButton);
        sortByIdButton = findViewById(R.id.sortByIDButton);
        welcomeView = findViewById(R.id.welcome);
        recyclerView = findViewById(R.id.shoppingLists);
    }


    @Override
    protected void onResume() {
        String userName = sharedPreferences.getString("user", null);
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .flatMap(user -> shoppingListDao.getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(list -> shoppingListAdapter.setLists(list)))
                .subscribe();
        super.onResume();
    }

    private void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void toProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    private void sortByAZ(View view) {
    }

    private void sortById(View view) {
        System.out.println("SORT");
    }

}