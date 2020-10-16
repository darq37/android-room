package com.darq37.android_room.database;

import android.content.Context;

import androidx.room.Room;

import com.darq37.android_room.database.room.AppDatabase;

public class RoomConstant {
    public static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "app_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }

        return INSTANCE;
    }

    public static synchronized void closeInstance() {
        if (INSTANCE != null) {
            if (INSTANCE.isOpen()) {
                INSTANCE.close();
            }
        }

        INSTANCE = null;
    }
}
