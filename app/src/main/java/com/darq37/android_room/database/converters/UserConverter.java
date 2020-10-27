package com.darq37.android_room.database.converters;

import androidx.room.TypeConverter;

import com.darq37.android_room.entity.User;


public class UserConverter {
    @TypeConverter
    public User from(String value) {
        User user = new User();
        user.setDisplayName(value);
        return user;
    }

    @TypeConverter
    public String to(User user) {
        return user.getDisplayName();
    }
}
