package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.RoomWarnings;
import androidx.room.TypeConverters;

import com.darq37.android_room.database.converters.UserConverter;

@SuppressWarnings({RoomWarnings.INDEX_FROM_EMBEDDED_ENTITY_IS_DROPPED, RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED})
@Entity(tableName = "shared_lists",
        primaryKeys = {"owner_id"}
)
public class SharedList {
    @TypeConverters(UserConverter.class)
    @ColumnInfo(name = "owner_id")
    @NonNull
    private User sharedList_owner;
    @Embedded
    private ShoppingList shoppingList;

    public SharedList() {
    }

    public void setSharedList_owner(@NonNull User sharedList_owner) {
        this.sharedList_owner = sharedList_owner;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public @NonNull
    User getSharedList_owner() {
        return sharedList_owner;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }
}
