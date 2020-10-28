package com.darq37.android_room.activities.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.product.ProductActivity;
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.adapters.ShoppingListAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private EditText listName;
    private User loggedInUser;
    private UserDao userDao;
    private ShoppingListDao shoppingListDao;
    private ProductDao productDao;
    private ShoppingListAdapter shoppingListAdapter;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        productDao = RoomConstant.getInstance(this).productDao();
        String user = sharedPreferences.getString("user", null);
        loggedInUser = userDao.getByIdSync(user);

        listName = findViewById(R.id.new_list_name);


        Button addProductButton = findViewById(R.id.goToProductActivity);
        Button addListButton = findViewById(R.id.new_list_button);
        Button addToListButton = findViewById(R.id.add_products_to_list_button);

        RecyclerView productRV = findViewById(R.id.productListView);
        RecyclerView shoppingListRV = findViewById(R.id.shoppingListsView);

        productRV.setHasFixedSize(true);
        shoppingListRV.setHasFixedSize(true);

        productRV.setLayoutManager(new LinearLayoutManager(this));
        shoppingListRV.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(productDao.getAllSync());
        shoppingListAdapter = new ShoppingListAdapter(shoppingListDao.getAllForUserSync(loggedInUser.getLogin()));

        productRV.setAdapter(productAdapter);
        shoppingListRV.setAdapter(shoppingListAdapter);


        addProductButton.setOnClickListener(this::goToProductActivity);
        addListButton.setOnClickListener(this::addNewList);
        addToListButton.setOnClickListener(this::addToList);
    }

    public void goToProductActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        startActivity(intent);
    }

    public void addNewList(View view) {
        String newListName = listName.getText().toString();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(newListName);
        shoppingList.setProducts(new ArrayList<>());
        shoppingList.setOwner(loggedInUser);
        shoppingList.setCreationDate(new Date());
        shoppingList.setModificationDate(new Date());
        shoppingListDao.insertSync(shoppingList);
        shoppingListAdapter.notifyDataSetChanged();
        listName.setText("");
    }

    public void addToList(View view) {
        List<Product> selectedProducts = productAdapter.getSelected();
        ShoppingList selectedList = shoppingListAdapter.getSelected();
        if (productAdapter.getSelected().size() > 0) {
            selectedList.setProducts(selectedProducts);
            shoppingListDao.updateSync(selectedList);
            productAdapter.notifyDataSetChanged();
            shoppingListAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No products selected", Toast.LENGTH_SHORT).show();
        }
    }
}