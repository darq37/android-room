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
}
