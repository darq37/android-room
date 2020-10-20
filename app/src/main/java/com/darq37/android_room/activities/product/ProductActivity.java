package com.darq37.android_room.activities.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    EditText productName;
    EditText productDescription;
    Button addProductButton;
    Button backButton;
    RecyclerView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        addProductButton = findViewById(R.id.addProductButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        backButton = findViewById(R.id.backToListActivity);

        addProductButton.setOnClickListener(this::addProduct);
        backButton.setOnClickListener(this::goBack);

        productList = findViewById(R.id.productListView);
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));

        getData();

    }

    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<Product>> {

            @Override
            protected List<Product> doInBackground(Void... voids) {
                return MainActivity.appDatabase.productDao().getAllSync();

            }

            @Override
            protected void onPostExecute(List<Product> data) {
                ProductAdapter adapter = new ProductAdapter(data);
                productList.setAdapter(adapter);
                super.onPostExecute(data);
            }
        }
        GetData gd = new GetData();
        gd.execute();
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

    public void goBack(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
    }
}