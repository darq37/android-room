package com.darq37.android_room.database.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Date from(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long to(Date date) {
        return date == null ? null : date.getTime();
    }
}
