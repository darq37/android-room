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
    private int checkedPosition = RecyclerView.NO_POSITION;

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
        holder.itemView.setBackgroundColor(checkedPosition == position ? Color.LTGRAY : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;

        public ListViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.listId_text);
            name = itemView.findViewById(R.id.listName_text);
            itemView.setOnClickListener(v1 -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                if (getAdapterPosition() == checkedPosition) {
                    checkedPosition = RecyclerView.NO_POSITION;
                    notifyDataSetChanged();
                }
                notifyItemChanged(checkedPosition);
                checkedPosition = getAdapterPosition();
                notifyItemChanged(checkedPosition);
            });
        }
    }

    public ShoppingList getSelected() {
        if (checkedPosition != RecyclerView.NO_POSITION) {
            return lists.get(checkedPosition);
        }
        return null;
    }
}

