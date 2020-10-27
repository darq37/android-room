package com.darq37.android_room.activities.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darq37.android_room.R;
import com.darq37.android_room.adapters.ProductAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.entity.Product;


public class ProductActivity extends AppCompatActivity {

    private EditText productName;
    private EditText productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Button addProductButton = findViewById(R.id.addProductButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);

        addProductButton.setOnClickListener(this::addProduct);

        RecyclerView productList = findViewById(R.id.productListView);
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter productAdapter =  new ProductAdapter(RoomConstant.getInstance(this).productDao().getAllSync());
        productList.setAdapter(productAdapter);

    }

    public void addProduct(View view) {
        String name = productName.getText().toString();
        String description = productDescription.getText().toString();
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        RoomConstant.getInstance(this).productDao().insertSync(p);
        Toast.makeText(getApplicationContext(), "Product saved", Toast.LENGTH_LONG).show();
        productName.setText("");
        productDescription.setText("");
    }

}