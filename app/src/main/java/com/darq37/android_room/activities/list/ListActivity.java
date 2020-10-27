package com.darq37.android_room.activities.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.product.ProductActivity;
import com.darq37.android_room.activities.product.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;


public class ListActivity extends AppCompatActivity {
    private EditText listName;
    private User loggedInUser;
    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        userDao = RoomConstant.getInstance(this).userDao();
        String user = sharedPreferences.getString("user", null);
        loggedInUser = userDao.getByIdSync(user);

        listName = findViewById(R.id.new_list_name);


        Button addProductButton = findViewById(R.id.goToProductActivity);
        Button addListButton = findViewById(R.id.new_list_button);

        RecyclerView productRV = findViewById(R.id.productListView);
        RecyclerView shoppingListRV = findViewById(R.id.shoppingListsView);

        productRV.setHasFixedSize(true);
        shoppingListRV.setHasFixedSize(true);

        productRV.setLayoutManager(new LinearLayoutManager(this));
        shoppingListRV.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter productAdapter = new ProductAdapter(RoomConstant.getInstance(this).productDao().getAllSync());
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(RoomConstant.getInstance(this).shoppingListDao().getAllForUserSync(loggedInUser.getLogin()));

        productRV.setAdapter(productAdapter);
        shoppingListRV.setAdapter(shoppingListAdapter);

        addProductButton.setOnClickListener(this::goToProductActivity);
        addListButton.setOnClickListener(this::addNewList);
    }

    public void goToProductActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        startActivity(intent);
    }

    public void addNewList(View view) {
        String newListName = listName.getText().toString();
        ShoppingList shoppingList = new ShoppingList();
        User owner = loggedInUser;
        shoppingList.setName(newListName);
        shoppingList.setProducts(new ArrayList<>());
        shoppingList.setOwner(owner);
        shoppingList.setCreationDate(new Date());
        shoppingList.setModificationDate(new Date());
        RoomConstant.getInstance(this).shoppingListDao().insertSync(shoppingList);
        listName.setText("");
    }
}