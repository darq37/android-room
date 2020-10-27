package com.darq37.android_room.activities.list;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.entity.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ListViewHolder> {

    public void setLists(List<ShoppingList> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    private List<ShoppingList> lists;
    private int checkedPosition = -1;

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
        holder.name.setSelected(checkedPosition == position);
        holder.name.setBackgroundColor(checkedPosition == position ? Color.GREEN : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;

        public ListViewHolder(View v) {
            super(v);
            id = v.findViewById(R.id.listId_text);
            name = v.findViewById(R.id.listName_text);
            name.setOnClickListener(v1 -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                notifyItemChanged(checkedPosition);
                checkedPosition = getAdapterPosition();
                notifyItemChanged(checkedPosition);
            });
        }
    }

    public ShoppingList getSelected() {
        if (checkedPosition != -1) {
            return lists.get(checkedPosition);
        }
        return null;
    }
}

