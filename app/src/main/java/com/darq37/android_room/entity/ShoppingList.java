package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.darq37.android_room.database.converters.DateConverter;
import com.darq37.android_room.database.converters.ListConverter;

import java.util.Date;
import java.util.List;

@Entity(
        tableName = "shopping_lists"
)
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "list_id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "list_name")
    private String name;

    @ColumnInfo(name = "owner")
    private User owner;

    @ColumnInfo(name = "products")
    @TypeConverters(ListConverter.class)
    private List<Product> products;

    @ColumnInfo(name = "creation_date")
    @TypeConverters(DateConverter.class)
    private Date creationDate;

    @ColumnInfo(name = "modification_date")
    @TypeConverters(DateConverter.class)
    private Date modificationDate;

    public ShoppingList() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

