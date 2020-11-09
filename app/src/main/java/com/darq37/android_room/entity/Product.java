package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "products",
        indices = {
                @Index(
                        value = {"product_name"},
                        unique = true
                )
        }
)
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "product_id")
    @SerializedName("id")
    @Expose
    private long id;

    @ColumnInfo(name = "product_name")
    @NonNull
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "product_description")
    @Nullable
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "product_creation_date")
    @SerializedName("created")
    @Expose
    private Date creationDate = new Date();

    @ColumnInfo(name = "product_modification_date")
    @SerializedName("edited")
    @Expose
    private Date modificationDate = new Date();

    @Ignore
    private boolean isChecked = false;

    public Product() {
    }

    @Ignore
    public Product(@NonNull String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    @Ignore
    public Product(@NonNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NonNull
    String getName() {
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
