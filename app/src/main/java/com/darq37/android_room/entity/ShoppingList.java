package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(
        tableName = "shopping_lists"
)
public class ShoppingList {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "list_id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "list_name")
    private String name;


    private User owner;

    private List<Product> products;

    private Date creationDate;

    private Date modificationDate;
}

