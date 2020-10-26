package com.darq37.android_room.activities.listDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.list.ShoppingListAdapter;
import com.darq37.android_room.activities.product.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.List;

public class ListDetailsActivity extends AppCompatActivity {

    private TextView textListName;
    private TextView textListOwner;
    private TextView textListDate;
    private RecyclerView productList;
    private UserDao userDao;
    private ShoppingListDao shoppingListDao;
    private ShoppingList list;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);


        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        userDao = RoomConstant.getInstance(this).userDao();
        user = userDao.getByIdSync(sharedPreferences.getString("user", null));
        list = shoppingListDao.getForUserSync(user.getLogin());

        textListName = findViewById(R.id.text_list_name);
        textListOwner = findViewById(R.id.text_list_owner);
        textListDate = findViewById(R.id.text_list_date);
        productList = findViewById(R.id.product_list);

        String name = list.getName();
        String nameString = String.format(getString(R.string.list_name_string), name);

        String owner = list.getOwner().getDisplayName();
        String ownerString = String.format(getString(R.string.list_owner_string), owner);

        String date = list.getCreationDate().toString();
        String dateString = String.format(getString(R.string.list_date_string), date);


        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));
        List<Product> products = list.getProducts();
        ProductAdapter productAdapter = new ProductAdapter(products);
        productList.setAdapter(productAdapter);


        textListName.setText(nameString);
        textListOwner.setText(ownerString);
        textListDate.setText(dateString);

    }
}