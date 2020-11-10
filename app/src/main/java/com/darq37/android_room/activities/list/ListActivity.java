package com.darq37.android_room.activities.list;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.darq37.android_room.R;
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ListActivity extends AppCompatActivity {
    private EditText listName;
    private User loggedInUser;
    private ShoppingListDao shoppingListDao;
    private ProductAdapter productAdapter;
    private Button addToListButton;
    private RecyclerView productRV;
    private UserDao userDao;
    private ProductDao productDao;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;


    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        initializeViews();
        initializeDao();
        fillRecyclerView();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        addToListButton.setOnClickListener(this::createList);
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void fillRecyclerView() {
        productDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(products -> {
                    productAdapter.setProductList(products);
                    productRV.setHasFixedSize(true);
                    productRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    productRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                    productRV.setAdapter(productAdapter);
                    String userName = sharedPreferences.getString("user", null);

                            userDao.getById(userName)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnSuccess(this::setLoggedInUser)
                                    .subscribe();
                        }
                )
                .subscribe();
    }


    private void refreshData() {
        final Handler handler = new Handler();
        productDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(products -> {
                    productAdapter.setProductList(products);
                    Toast.makeText(this, "Data refreshed.", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }).doOnError(error -> handler.postDelayed(() -> {
            Toast.makeText(this, "Cannot refresh the data.", Toast.LENGTH_SHORT).show();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500)).subscribe();
    }

    private void initializeDao() {
        userDao = RoomConstant.getInstance(this).userDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
        productDao = RoomConstant.getInstance(this).productDao();
    }

    private void initializeViews() {
        listName = findViewById(R.id.new_list_name);
        addToListButton = findViewById(R.id.create_list_button);
        productRV = findViewById(R.id.productListView);
        swipeRefreshLayout = findViewById(R.id.account_swipe_refresh);
        productAdapter = new ProductAdapter(Collections.emptyList());
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

            shoppingListDao.insert(shoppingList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(response -> {
                        Toast.makeText(this, "List created successfully!", Toast.LENGTH_SHORT).show();
                        listName.setText("");
                    })
                    .subscribe();
        } else {
            Toast.makeText(this, "Select 1 or more products!", Toast.LENGTH_SHORT).show();
        }
    }

}