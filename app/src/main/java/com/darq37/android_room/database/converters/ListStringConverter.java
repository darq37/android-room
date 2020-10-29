package com.darq37.android_room.database.converters;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListStringConverter {

    @TypeConverter
    public List<String> fromString(String value) {
        return value == null || value.isEmpty() ? Collections.emptyList() : Arrays.stream(value.split(", "))
                .map(String::new)
                .collect(Collectors.toList());
    }

    @TypeConverter
    public String toString(List<String> list) {
        return list == null || list.isEmpty() ? null : list
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }


}
