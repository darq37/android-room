package com.darq37.android_room.activities.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.list.ListActivity;
import com.darq37.android_room.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private EditText productName;
    private EditText productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Button addProductButton = findViewById(R.id.addProductButton);
        FloatingActionButton backButton = findViewById(R.id.backToListActivity);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);

        addProductButton.setOnClickListener(this::addProduct);
        backButton.setOnClickListener(this::goToListActivity);

        RecyclerView productList = findViewById(R.id.productListView);
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter productAdapter =  new ProductAdapter(MainActivity.appDatabase.productDao().getAllSync());
        productList.setAdapter(productAdapter);

    }

    public void addProduct(View view) {
        String name = productName.getText().toString();
        String description = productDescription.getText().toString();
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        MainActivity.appDatabase.productDao().insertSync(p);
        Toast.makeText(getApplicationContext(), "Product saved", Toast.LENGTH_LONG).show();
        productName.setText("");
        productDescription.setText("");
    }

    public void goToListActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
    }
}