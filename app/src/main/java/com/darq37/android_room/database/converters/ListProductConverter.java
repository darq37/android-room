package com.darq37.android_room.database.converters;

import androidx.room.TypeConverter;

import com.darq37.android_room.entity.Product;

import java.util.Collections;
import java.util.List;

public class ListProductConverter {

    @TypeConverter
    public List<Product> from(String value) {
        if (value == null) {
            return Collections.emptyList();
        } else {
            return Product.getGson().fromJson(value, Product.LIST_TYPE);
        }
    }

    @TypeConverter
    public String to(List<Product> value) {
        return value == null || value.isEmpty() ? null : Product.getGson().toJson(value);
    }
}
