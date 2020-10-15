package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "products",
        indices = {
                @Index(
                        value = {"product_name"},
                        unique = true
                )
        }
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "product_id")
    private Long id;

    @ColumnInfo(name = "product_name")
    @NonNull
    private String name;

    @ColumnInfo
    @Nullable
    private String description;

    @ColumnInfo(name = "creation_date")
    private Date creationDate =  new Date();

    @ColumnInfo(name = "modification_date")
    private Date modificationDate =  new Date();

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
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
