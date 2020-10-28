package com.darq37.android_room.activities.list;

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
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.Date;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private EditText listName;
    private User loggedInUser;
    private ShoppingListDao shoppingListDao;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", null);

        UserDao userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        ProductDao productDao = RoomConstant.getInstance(this).productDao();

        productAdapter = new ProductAdapter(productDao.getAllSync());
        loggedInUser = userDao.getByIdSync(user);

        listName = findViewById(R.id.new_list_name);
        Button addToListButton = findViewById(R.id.create_list_button);
        RecyclerView productRV = findViewById(R.id.productListView);

        productRV.setHasFixedSize(true);
        productRV.setLayoutManager(new LinearLayoutManager(this));
        productRV.setAdapter(productAdapter);

        addToListButton.setOnClickListener(this::createList);
    }


    public void createList(View view) {
        String newListName = listName.getText().toString();
        List<Product> selectedProducts = productAdapter.getSelected();
        if (selectedProducts.size() > 0) {
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setName(newListName);
            shoppingList.setProducts(selectedProducts);
            shoppingList.setOwner(loggedInUser);
            shoppingList.setCreationDate(new Date());
            shoppingList.setModificationDate(new Date());
            shoppingListDao.insertSync(shoppingList);
            Toast.makeText(this, "List created successfully!", Toast.LENGTH_SHORT).show();
            listName.setText("");
        } else {
            Toast.makeText(this, "Select 1 or more products!", Toast.LENGTH_SHORT).show();
        }
    }

}