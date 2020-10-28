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


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_data, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @SuppressLint("SetTextI18n")
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView description;


        public ProductViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.productIdText);
            name = view.findViewById(R.id.productNameText);
            description = view.findViewById(R.id.productDescriptionText);
        }

        void bind(final Product p) {
            id.setText(Long.toString(p.getId()));
            description.setText(p.getDescription());
            name.setText(p.getName());

            itemView.setOnClickListener(v -> {
                p.setChecked(!p.isChecked());
                itemView.setBackgroundColor(p.isChecked() ? Color.LTGRAY : Color.TRANSPARENT);
                notifyDataSetChanged();
            });

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
}
