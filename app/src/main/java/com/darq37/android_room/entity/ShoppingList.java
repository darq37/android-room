package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;
import androidx.room.TypeConverters;

import com.darq37.android_room.database.converters.DateConverter;
import com.darq37.android_room.database.converters.ListProductConverter;

import java.util.Date;
import java.util.List;

@SuppressWarnings({RoomWarnings.INDEX_FROM_EMBEDDED_ENTITY_IS_DROPPED, RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED})
@Entity(
        tableName = "shopping_lists"
)
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "list_id")
    private long id;

    @NonNull
    @ColumnInfo(name = "list_name")
    private String name;

    @Embedded
    private User owner;

    @ColumnInfo(name = "products")
    @TypeConverters(ListProductConverter.class)
    private List<Product> products;

    @ColumnInfo(name = "product_creation_date")
    @TypeConverters(DateConverter.class)
    private Date creationDate;

    @ColumnInfo(name = "product_modification_date")
    @TypeConverters(DateConverter.class)
    private Date modificationDate;

    public ShoppingList() {
    }

    @Ignore
    public ShoppingList(@NonNull Long id, @NonNull String name, User owner, List<Product> products, Date creationDate, Date modificationDate) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.products = products;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
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

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }
}

