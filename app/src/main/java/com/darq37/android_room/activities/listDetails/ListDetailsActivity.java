package com.darq37.android_room.activities.listDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.SharedListDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListDetailsActivity extends AppCompatActivity {

    private UserDao userDao;
    private ShoppingList list;
    private EditText userToShare;
    private SharedListDao sharedListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        Intent intent = getIntent();
        long list_id = intent.getLongExtra("list_id", 1);


        ShoppingListDao shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        sharedListDao = RoomConstant.getInstance(this).sharedListDao();
        userDao = RoomConstant.getInstance(this).userDao();
        list = shoppingListDao.getByIdSync(list_id);

        TextView textListName = findViewById(R.id.text_list_name);
        TextView textListOwner = findViewById(R.id.text_list_owner);
        TextView textListDate = findViewById(R.id.text_list_date);
        RecyclerView productList = findViewById(R.id.product_list);
        FloatingActionButton shareButton = findViewById(R.id.shareButton);
        userToShare = findViewById(R.id.userToShare);

        String name = list.getName();
        String nameString = String.format(getString(R.string.list_name_string), name);

        String owner = list.getOwner().getDisplayName();
        String ownerString = String.format(getString(R.string.list_owner_string), owner);

        String date = list.getCreationDate().toString();
        String dateString = String.format(getString(R.string.list_created_date_string), date);


        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter productAdapter = new ProductAdapter(list.getProducts());
        productList.setAdapter(productAdapter);


        textListName.setText(nameString);
        textListOwner.setText(ownerString);
        textListDate.setText(dateString);

        shareButton.setOnClickListener(this::share);

    }

    public void share(View view) {
        String username = userToShare.getText().toString();
        if (userDao.getByNameSync(username) != null) {
            SharedList sharedList = new SharedList();
            sharedList.setShoppingList(list);
            sharedList.setSharedList_owner(userDao.getByNameSync(username));
            sharedListDao.insertSync(sharedList);
            Toast.makeText(this, "List shared", Toast.LENGTH_LONG).show();
        } else {
            String msg = String.format("User '%s' doesn't exist.", username);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

    }
}