package com.darq37.android_room.activities.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.entity.ShoppingList;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ListViewHolder> {

    private List<ShoppingList> lists;

    public ShoppingListAdapter(List<ShoppingList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data, parent, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ShoppingList shoppingList = lists.get(position);
        holder.id.setText(Long.toString(shoppingList.getId()));
        holder.name.setText(shoppingList.getName());
        holder.owner.setText(shoppingList.getOwner().getDisplayName());
        holder.products.setText(shoppingList.getProducts().toString());
        holder.created.setText(shoppingList.getCreationDate().toString());
        holder.updated.setText(shoppingList.getModificationDate().toString());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView owner;
        private TextView products;
        private TextView created;
        private TextView updated;

        public ListViewHolder(View v) {
            super(v);
            id = v.findViewById(R.id.listId_text);
            name = v.findViewById(R.id.listName_text);
            owner = v.findViewById(R.id.listOwner_text);
            products = v.findViewById(R.id.listProducts_text);
            created = v.findViewById(R.id.listCreated_text);
            updated = v.findViewById(R.id.listUpdated_text);
        }

    }
}
