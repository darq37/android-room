package com.darq37.android_room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "shared_lists",
        primaryKeys = {"list_id", "user_id"}
)
public class SharedList {
    @ColumnInfo(name = "user_id")
    private User user;
    @ColumnInfo(name = "list_id")
    private ShoppingList shoppingList;

    public SharedList() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public User getUser() {
        return user;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }
}
