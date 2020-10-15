package com.darq37.android_room.database.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListConverter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public List<String> fromString(String list){
        return list == null || list.isEmpty() ? Collections.emptyList() : Arrays.stream(list.split(", "))
                .map(String::new)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public String toString(List<String> list){
        return list ==null || list.isEmpty() ? null : list
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }


}
