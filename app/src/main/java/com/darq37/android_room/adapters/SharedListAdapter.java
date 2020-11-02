package com.darq37.android_room.adapters;

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
import com.darq37.android_room.entity.SharedList;

import java.util.List;

public class SharedListAdapter extends RecyclerView.Adapter<SharedListAdapter.SharedListViewHolder> {
    private List<SharedList> sharedListList;


    public SharedListAdapter(List<SharedList> sharedListList) {
        this.sharedListList = sharedListList;
    }

    @NonNull
    @Override
    public SharedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.shared_list_data, parent, false);
        return new SharedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedListViewHolder holder, int position) {
        SharedList sharedList = sharedListList.get(position);
        holder.name.setText(sharedList.getShoppingList().getName());
        holder.from.setText(sharedList.getShoppingList().getOwner().getDisplayName());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ListDetailsActivity.class);
            intent.putExtra("list_id", sharedList.getShoppingList().getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sharedListList.size();
    }

    public void setLists(List<SharedList> lists) {
        this.sharedListList = lists;
        notifyDataSetChanged();
    }

    public static class SharedListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView from;

        public SharedListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shared_list_name);
            from = itemView.findViewById(R.id.from);

        }
    }
}
