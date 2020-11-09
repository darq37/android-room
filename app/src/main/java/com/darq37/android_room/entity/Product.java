package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "products",
        indices = {
                @Index(
                        value = {"product_name"},
                        unique = true
                )
        }
)
public class Product implements Serializable {
    private static final long serialVersionUID = -2316417636992806867L;
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

    public static final Type LIST_TYPE = new TypeToken<List<Product>>() {
    }.getType();

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
