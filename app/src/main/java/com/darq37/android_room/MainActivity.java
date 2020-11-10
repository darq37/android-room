package com.darq37.android_room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.activities.account.AccountActivity;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.activities.product.ProductActivity;
import com.darq37.android_room.adapters.ShoppingListAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.SharedListDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;
import com.darq37.android_room.service.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private ShoppingListAdapter shoppingListAdapter;
    private Button logout;
    private ImageButton sortByAZButton;
    private ImageButton sortByIdButton;
    private FloatingActionButton settingsButton;
    private FloatingActionButton addNewListButton;
    private FloatingActionButton addProductButton;
    private FloatingActionButton syncButton;
    private TextView welcomeView;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private UserDao userDao;
    private ShoppingListDao shoppingListDao;
    private ProductDao productDao;
    private SharedListDao sharedListDao;
    private String userName;
    private boolean sortedAlphabetically;
    private boolean sortedByID;
    private ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        findViewsById();
        initializeDaos();
        syncUsers();
        setupRecyclerView();
        setOnClickListeners();
    }

    private void initializeVariables() {

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        userName = sharedPreferences.getString("user", null);
        apiService = ApiService.getApiService(getApplicationContext());
        shoppingListAdapter = new ShoppingListAdapter(Collections.emptyList());
        sortedAlphabetically = false;
        sortedByID = false;

    }

    private void findViewsById() {
        logout = findViewById(R.id.logOutButton);
        settingsButton = findViewById(R.id.settingsButton);
        addNewListButton = findViewById(R.id.to_new_list_button);
        addProductButton = findViewById(R.id.to_products_button);
        sortByAZButton = findViewById(R.id.sortByAlphaButton);
        sortByIdButton = findViewById(R.id.sortByIDButton);
        syncButton = findViewById(R.id.syncButton);
        welcomeView = findViewById(R.id.welcome);
        recyclerView = findViewById(R.id.shoppingLists);
    }

    private void initializeDaos() {
        userDao = RoomConstant.getInstance(this).userDao();
        productDao = RoomConstant.getInstance(this).productDao();
        sharedListDao = RoomConstant.getInstance(this).sharedListDao();
        shoppingListDao = RoomConstant.getInstance(this).shoppingListDao();
    }

    private void setOnClickListeners() {
        logout.setOnClickListener(this::logout);
        settingsButton.setOnClickListener(this::toAccountActivity);
        addNewListButton.setOnClickListener(this::toListActivity);
        addProductButton.setOnClickListener(this::toProductActivity);
        sortByIdButton.setOnClickListener(this::sortById);
        sortByAZButton.setOnClickListener(this::sortByAZ);
        syncButton.setOnClickListener(this::syncData);
    }

    private void setupRecyclerView() {
        Resources res = getResources();
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(user -> {
                    String displayName = user.getDisplayName();
                    String welcomeString = String.format(res.getString(R.string.welcomeString), displayName);
                    welcomeView.setText(welcomeString);
                })
                .doOnSuccess(user -> shoppingListDao
                        .getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(result -> {
                            shoppingListAdapter.setLists(result);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(shoppingListAdapter);
                        }).subscribe())
                .subscribe();
    }


    private void syncData(View view) {
        syncUsers();
        syncProducts();
        syncSharedLists();
        syncShoppingLists();
    }

    private void syncUsers() {

        userDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(users -> apiService.postUsers(users)
                        .doOnSuccess(array -> apiService
                                .getUsers()
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .doOnSuccess(jsonArray -> {
                                    Gson gson = new Gson();
                                    User[] userArray = gson.fromJson(jsonArray, User[].class);
                                    userDao.clearAll()
                                            .doOnComplete(() -> userDao
                                                    .insert(Arrays.asList(userArray))
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .doOnSuccess(longs -> System.out.println("Users synced"))
                                                    .subscribe())
                                            .subscribe();
                                })
                                .doOnError(Log::getStackTraceString)
                                .subscribe())
                        .doOnComplete(() -> Toast.makeText(this, "Cannot save users to the back-end", Toast.LENGTH_SHORT).show())
                        .ignoreElement()
                        .onErrorComplete(throwable -> true)
                        .subscribe()).subscribe();
    }


    private void syncProducts() {
        productDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(products -> apiService.postProducts(products)
                        .doOnSuccess(array ->
                                apiService.getProducts()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .doOnSuccess(jsonArray -> {
                                            Gson gson = new Gson();
                                            Product[] productArray = gson.fromJson(jsonArray, Product[].class);
                                            productDao.clearAll()
                                                    .doOnComplete(() -> productDao
                                                            .insert(Arrays.asList(productArray))
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .doOnSuccess(longs -> System.out.println("Products synced"))
                                                            .subscribe())
                                                    .subscribe();
                                        })
                                        .doOnComplete(() -> Toast.makeText(this, "Cannot get products from back-end", Toast.LENGTH_SHORT).show())
                                        .ignoreElement()
                                        .onErrorComplete(throwable -> true)
                                        .subscribe())
                        .doOnComplete(() -> Toast.makeText(this, "Cannot save products to the back-end", Toast.LENGTH_SHORT).show())
                        .ignoreElement()
                        .onErrorComplete(throwable -> true)
                        .subscribe()).subscribe();
    }

    private void syncShoppingLists() {
        shoppingListDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(shoppingLists -> apiService.postShoppingLists(shoppingLists)
                        .doOnSuccess(array ->
                                apiService.getShoppingLists()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .doOnSuccess(jsonArray -> {
                                            Gson gson = new Gson();
                                            ShoppingList[] shoppingListArray = gson.fromJson(jsonArray, ShoppingList[].class);
                                            shoppingListDao.clearAll()
                                                    .doOnComplete(() -> shoppingListDao
                                                            .insert(Arrays.asList(shoppingListArray))
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .doOnSuccess(longs -> {
                                                                System.out.println("Shopping lists synced");
                                                                shoppingListDao.getAllForUser(userName)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .doOnSuccess(lists -> shoppingListAdapter.setLists(lists))
                                                                        .subscribe();
                                                            })
                                                            .subscribe())
                                                    .subscribe();
                                        })
                                        .doOnComplete(() -> Toast.makeText(this, "Cannot get shopping lists from back-end", Toast.LENGTH_SHORT).show())
                                        .ignoreElement()
                                        .onErrorComplete(throwable -> true)
                                        .subscribe())
                        .doOnComplete(() -> Toast.makeText(this, "Cannot save shopping lists to the back-end", Toast.LENGTH_SHORT).show())
                        .ignoreElement()
                        .onErrorComplete(throwable -> true)
                        .subscribe()).subscribe();

    }

    private void syncSharedLists() {
        sharedListDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(sharedLists -> apiService.postShared(sharedLists)
                        .doOnSuccess(array ->
                                apiService.getSharedLists()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .doOnSuccess(jsonArray -> {
                                            Gson gson = new Gson();
                                            SharedList[] sharedListArray = gson.fromJson(jsonArray, SharedList[].class);
                                            sharedListDao.clearAll()
                                                    .doOnComplete(() -> sharedListDao
                                                            .insert(Arrays.asList(sharedListArray))
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .doOnSuccess(longs -> System.out.println("Shared lists synced"))
                                                            .subscribe())
                                                    .subscribe();
                                        })
                                        .doOnComplete(() -> Toast.makeText(this, "Cannot get shared lists from back-end", Toast.LENGTH_SHORT).show())
                                        .ignoreElement()
                                        .onErrorComplete(throwable -> true)
                                        .subscribe())
                        .doOnComplete(() -> Toast.makeText(this, "Cannot save shared lists to the back-end", Toast.LENGTH_SHORT).show())
                        .ignoreElement()
                        .onErrorComplete(throwable -> true)
                        .subscribe()).subscribe();
    }

    private void sortByAZ(View view) {
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(user -> shoppingListDao.getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(list -> {
                            if (!sortedAlphabetically) {
                                list.sort((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
                                shoppingListAdapter.setLists(list);
                                sortedAlphabetically = true;
                            } else {
                                list.sort((o1, o2) -> o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase()));
                                shoppingListAdapter.setLists(list);
                                sortedAlphabetically = false;
                            }
                        }).subscribe())
                .subscribe();
    }

    private void sortById(View view) {
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(user -> shoppingListDao.getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(list -> {
                            if (!sortedByID) {
                                list.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
                                shoppingListAdapter.setLists(list);
                                sortedByID = true;
                            } else {
                                list.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
                                shoppingListAdapter.setLists(list);
                                sortedByID = false;
                            }
                        }).subscribe())
                .subscribe();
    }

    @Override
    protected void onResume() {
        String userName = sharedPreferences.getString("user", null);
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(user -> shoppingListDao.getAllForUser(user.getLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(list -> shoppingListAdapter.setLists(list))
                        .subscribe())
                .subscribe();
        super.onResume();
    }

    private void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        sharedPreferences.edit().putBoolean("logged", false).apply();
        startActivity(intent);
    }

    private void toAccountActivity(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void toListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void toProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

}