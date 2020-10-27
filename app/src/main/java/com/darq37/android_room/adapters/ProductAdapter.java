package com.darq37.android_room.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public void setProductList(ArrayList<Product> productList) {
        this.productList = new ArrayList<>();
        this.productList = productList;
        notifyDataSetChanged();
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_data, parent, false);

        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.id.setText(Long.toString(p.getId()));
        holder.description.setText(p.getDescription());
        holder.name.setText(p.getName());
        holder.itemView.setBackgroundColor(p.isChecked() ? Color.LTGRAY : Color.TRANSPARENT);
        holder.itemView.setOnClickListener(v -> p.setChecked(!p.isChecked()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView description;


        public ProductViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.productIdText);
            name = view.findViewById(R.id.productNameText);
            description = view.findViewById(R.id.productDescriptionText);

        }
    }

    public List<Product> getSelected() {
        List<Product> selected = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).isChecked()) {
                selected.add(productList.get(i));
            }
        }
        return selected;
    }

    public List<Product> getAll() {
        return productList;
    }
}
