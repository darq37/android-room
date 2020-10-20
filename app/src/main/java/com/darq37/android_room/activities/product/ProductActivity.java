package com.darq37.android_room.activities.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.darq37.android_room.R;

public class ProductActivity extends AppCompatActivity {

    EditText productName;
    EditText productDescription;
    Button addProductButton;
    RecyclerView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        addProductButton = findViewById(R.id.addProductButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productList = findViewById(R.id.productListView);


    }
}