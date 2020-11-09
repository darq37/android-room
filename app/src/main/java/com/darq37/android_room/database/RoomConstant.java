package com.darq37.android_room.database;

import android.content.Context;

import androidx.room.Room;

import com.darq37.android_room.database.room.AppDatabase;

public class RoomConstant {
    public static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "app_db")
                .fallbackToDestructiveMigration()
                /*.addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        db.execSQL("INSERT INTO users(login, display_name, password, user_creation_date, user_modification_date) VALUES ('login', 'Dominik', 'password', 776995200000, 776995200000)");
                        db.execSQL("INSERT INTO products(product_name, product_description) VALUES ('Duck', 'Regular rubber duck')");
                        db.execSQL("INSERT INTO products(product_name, product_description) VALUES ('Carrot', 'Delicious carrot')");
                        db.execSQL("INSERT INTO products(product_name, product_description) VALUES ('Pistol', 'Deadly weapon')");
                        db.execSQL("INSERT INTO products(product_name, product_description) VALUES ('Toilet paper', 'Extra soft toilet paper')");
                    }
                })*/
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
