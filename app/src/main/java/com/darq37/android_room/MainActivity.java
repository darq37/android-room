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
import com.darq37.android_room.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    public User loggedInUser;
    private ShoppingListAdapter shoppingListAdapter;
    private ShoppingListDao shoppingListDao;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        UserDao userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();

        Button logout = findViewById(R.id.logOutButton);
        FloatingActionButton settingsButton = findViewById(R.id.settingsButton);
        FloatingActionButton addNewListButton = findViewById(R.id.to_new_list_button);
        FloatingActionButton addProductButton = findViewById(R.id.to_products_button);
        TextView welcomeView = findViewById(R.id.welcome);
        RecyclerView recyclerView = findViewById(R.id.shoppingLists);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String userLogin = sharedPreferences.getString("user", null);

        userDao.getById(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(response -> {
                    String displayName = response.getDisplayName();
                    String welcomeString = String.format(res.getString(R.string.welcomeString), displayName);
                    welcomeView.setText(welcomeString);
                    shoppingListDao.getAllForUser(response.getLogin())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess(resp -> {
                                shoppingListAdapter = new ShoppingListAdapter(resp);
                                recyclerView.setAdapter(shoppingListAdapter);
                            })
                            .subscribe();


                })
                .subscribe();


        logout.setOnClickListener(this::logout);
        settingsButton.setOnClickListener(this::toAccountActivity);
        addNewListButton.setOnClickListener(this::toListActivity);
        addProductButton.setOnClickListener(this::toProductActivity);
    }

/*    @Override
    protected void onResume() {
        shoppingListDao
                .getAllForUser(sharedPreferences.getString("user", null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(p -> shoppingListAdapter.setLists(p))
                .subscribe();
        super.onResume();
    }*/

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