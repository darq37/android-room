package com.darq37.android_room.activities.listDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.adapters.ProductListAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.SharedListDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListDetailsActivity extends AppCompatActivity {

    private UserDao userDao;
    private EditText userToShare;
    private SharedListDao sharedListDao;
    private TextView textListName;
    private FloatingActionButton shareButton;
    private TextView textListOwner;
    private TextView textListDate;
    private RecyclerView productList;
    private ProductListAdapter productAdapter;
    private ShoppingList shoppingList;

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        Intent intent = getIntent();
        long list_id = intent.getLongExtra("list_id", 1);


        ShoppingListDao shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        sharedListDao = RoomConstant.getInstance(this).sharedListDao();
        userDao = RoomConstant.getInstance(this).userDao();
        productAdapter = new ProductListAdapter(Collections.emptyList());
        initializeViews();

        shoppingListDao.getById(list_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(list -> {
                    String name = list.getName();
                    String nameString = String.format(getString(R.string.list_name_string), name);

                    String owner = list.getOwner().getDisplayName();
                    String ownerString = String.format(getString(R.string.list_owner_string), owner);

                    String date = list.getCreationDate().toString();
                    String dateString = String.format(getString(R.string.list_created_date_string), date);

                    textListName.setText(nameString);
                    textListOwner.setText(ownerString);
                    textListDate.setText(dateString);

                    productList.setHasFixedSize(true);
                    productList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    productAdapter.setProductList(list.getProducts());
                    productList.setAdapter(productAdapter);

                    setShoppingList(list);
                })
                .subscribe();

        shareButton.setOnClickListener(this::share);

    }

    private void initializeViews() {
        textListName = findViewById(R.id.text_list_name);
        textListOwner = findViewById(R.id.text_list_owner);
        textListDate = findViewById(R.id.text_list_date);
        productList = findViewById(R.id.product_list);
        shareButton = findViewById(R.id.shareButton);
        userToShare = findViewById(R.id.userToShare);
    }

    public void share(View view) {
        String username = userToShare.getText().toString();
        if (!username.isEmpty()) {
            SharedList sharedList = new SharedList();
            userDao.getByName(username)
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(res -> {
                        sharedList.setSharedList_owner(res);
                        sharedList.setShoppingList(shoppingList);
                        sharedListDao.insert(sharedList)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSuccess(result -> Toast.makeText(this, "List shared", Toast.LENGTH_LONG).show())
                                .doOnError(response -> {
                                    String msg = String.format("User '%s' doesn't exist.", username);
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                })
                                .subscribe();
                    })
                    .subscribe();
        }
    }
}