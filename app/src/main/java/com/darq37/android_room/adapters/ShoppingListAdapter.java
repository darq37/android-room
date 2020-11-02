package com.darq37.android_room.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.listDetails.ListDetailsActivity;
import com.darq37.android_room.entity.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ListViewHolder> {

    public void setLists(List<ShoppingList> lists) {
        this.lists = new ArrayList<>();
        this.lists = lists;
        notifyDataSetChanged();
    }

    private List<ShoppingList> lists;

    public ShoppingListAdapter(List<ShoppingList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_data, parent, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ShoppingList shoppingList = lists.get(position);
        holder.id.setText(Long.toString(shoppingList.getId()));
        holder.name.setText(shoppingList.getName());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ListDetailsActivity.class);
            intent.putExtra("list_id", shoppingList.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;

        public ListViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.listId_text);
            name = itemView.findViewById(R.id.listName_text);
        }
    }
}

