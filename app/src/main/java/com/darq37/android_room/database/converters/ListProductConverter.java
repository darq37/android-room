package com.darq37.android_room.database.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.darq37.android_room.entity.Product;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListProductConverter {

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Product> from(String value) {
        if (value == null) {
            return Collections.emptyList();
        } else {
            String[] elements = value.split(", ");
            List<Product> products = new ArrayList<>();
            for (String s :
                    elements) {
                products.add(new Product(s));
            }
            return products;
        }

    }

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String to(List<Product> value) {
        return value == null || value.isEmpty() ? null : value
                .stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
    }
}
