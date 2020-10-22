package com.darq37.android_room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.darq37.android_room.R;
import com.darq37.android_room.database.room.AppDatabase;
import com.darq37.android_room.entity.User;

import java.util.concurrent.Executors;

public class RoomConstant {
    public static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "app_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                User user =  new User("testLogin", "password", "displayUser");
                                INSTANCE.userDao().insert(user);

                            }
                        });
                    }
                })
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
