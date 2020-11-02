package com.darq37.android_room.activities.product;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.darq37.android_room.R;
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.entity.Product;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ProductActivity extends AppCompatActivity {

    private EditText productName;
    private EditText productDescription;
    private Button addProductButton;
    private ProductDao productDao;
    private ProductAdapter productAdapter;
    private RecyclerView productList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productDao = RoomConstant.getInstance(this).productDao();

        initializeViews();

        productDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(products -> {
                    productAdapter.setProductList(products);
                    productList.setHasFixedSize(true);
                    productList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    productList.setAdapter(productAdapter);
                })
                .subscribe();

        addProductButton.setOnClickListener(this::addProduct);
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
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

    private void initializeViews() {
        productList = findViewById(R.id.productListView);
        addProductButton = findViewById(R.id.addProductButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        swipeRefreshLayout = findViewById(R.id.product_swipe_refresh);
        productAdapter = new ProductAdapter(Collections.emptyList());
    }

    public void addProduct(View view) {
        String name = productName.getText().toString();
        String description = productDescription.getText().toString();
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        productDao.insert(p)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(response -> {
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Product saved", Toast.LENGTH_LONG).show();
                    productName.setText("");
                    productDescription.setText("");
                })
                .subscribe();

    }
}