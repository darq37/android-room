package com.darq37.android_room.activities.list;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.entity.ShoppingList;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ListViewHolder> {

    private List<ShoppingList> lists;
    private RoomConstant rm;

    public ShoppingListAdapter(List<ShoppingList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ListViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
