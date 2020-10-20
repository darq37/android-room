package com.darq37.android_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ShoppingListAdapter;
import com.darq37.android_room.activities.login.data.model.LoggedInUser;
import com.darq37.android_room.activities.login.ui.login.LoginActivity;
import com.darq37.android_room.activities.shared.SharedActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.room.AppDatabase;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private AppDatabase appDatabase;
    private RecyclerView.Adapter shoppingListAdapter;
    private RecyclerView.LayoutManager layoutManager;
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
        TextView welcomeView = findViewById(R.id.welcome);
        String welcomeMsg = "Welcome " + loggedInUser.getDisplayName();

        RecyclerView recyclerView = findViewById(R.id.shoppingLists);
        layoutManager =  new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        shoppingListAdapter =  new ShoppingListAdapter(appDatabase.shoppingListDao().getAllSync());
        recyclerView.setAdapter(shoppingListAdapter);


        welcomeView.setText(welcomeMsg);
        logOut.setOnClickListener(this::toLoginActivity);
        settings.setOnClickListener(this::toAccountActivity);
        share.setOnClickListener(this::toShareActivity);


        //Populating DB for testing:
        Product p  = new Product("Potato", "Just regular potato.");
        User user =  new User("Admin", "AdminName", "Password");
        List<Product> products =  new ArrayList<>();
        products.add(p);
        ShoppingList list =  new ShoppingList("name", user, products);
        appDatabase.productDao().insertSync(p);


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
    public void toShareActivity(View view){
        Intent intent =  new Intent(this, SharedActivity.class);
        startActivity(intent);
    }









    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}