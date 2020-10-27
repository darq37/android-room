package com.darq37.android_room.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.entity.SharedList;

import java.util.ArrayList;
import java.util.List;

public class SharedListAdapter extends RecyclerView.Adapter<SharedListAdapter.SharedListViewHolder> {
    private List<SharedList> sharedListList;
    private int checkedPosition = RecyclerView.NO_POSITION;

    public void setSharedListList(ArrayList<SharedList> list) {
        this.sharedListList = new ArrayList<>();
        this.sharedListList = list;
        notifyDataSetChanged();
    }

    public SharedListAdapter(List<SharedList> sharedListList) {
        this.sharedListList = sharedListList;
    }

    @NonNull
    @Override
    public SharedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_list_data, parent, false);
        return new SharedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedListViewHolder holder, int position) {
        SharedList sharedList = sharedListList.get(position);
        holder.name.setText(sharedList.getShoppingList().getName());
        holder.owner.setText(sharedList.getSharedList_owner().getDisplayName());
        holder.itemView.setSelected(checkedPosition == position);
        holder.itemView.setBackgroundColor(checkedPosition == position ? Color.LTGRAY : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return sharedListList.size();
    }

    public class SharedListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView owner;

        public SharedListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shared_list_name);
            owner = itemView.findViewById(R.id.shared_list_owner);
            itemView.setOnClickListener(v -> {
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

    public SharedList getSelected() {
        if (checkedPosition != RecyclerView.NO_POSITION) {
            return sharedListList.get(checkedPosition);
        }
        return null;
    }
}
