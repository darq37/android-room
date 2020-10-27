package com.darq37.android_room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.adapters.ShoppingListAdapter;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.SharedListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    public User loggedInUser;
    private UserDao userDao;
    private SharedListDao sharedListDao;
    private EditText userToShare;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();
        userDao = RoomConstant.getInstance(this).userDao();
        sharedListDao = RoomConstant.getInstance(this).sharedListDao();

        Button logout = findViewById(R.id.logOutButton);
        FloatingActionButton settings = findViewById(R.id.settingsButton);
        FloatingActionButton share = findViewById(R.id.shareButton);
        FloatingActionButton lists = findViewById(R.id.goToListsButton);
        userToShare = findViewById(R.id.userToShare);
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
        settings.setOnClickListener(this::toAccountActivity);
        lists.setOnClickListener(this::toListActivity);
        share.setOnClickListener(this::share);


    }


    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        loggedInUser = null;
        startActivity(intent);
    }

    public void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void share(View view) {
        String username = userToShare.getText().toString();
        ShoppingList toShare = shoppingListAdapter.getSelected();
        if (toShare == null) {
            Toast.makeText(this, "Pick a list to share!", Toast.LENGTH_LONG).show();
        } else {
            SharedList sharedList = new SharedList();
            sharedList.setShoppingList(toShare);
            sharedList.setSharedList_owner(userDao.getByNameSync(username));
            sharedListDao.insert(sharedList);
            Toast.makeText(this, "List shared", Toast.LENGTH_LONG).show();
        }
    }

    public void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}