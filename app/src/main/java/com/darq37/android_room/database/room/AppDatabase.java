package com.darq37.android_room.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.darq37.android_room.database.converters.DateConverter;
import com.darq37.android_room.database.converters.ListConverter;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.SharedShoppingList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;


@Database(
        entities = {
                User.class,
                Product.class,
                ShoppingList.class,
                SharedShoppingList.class
        },
        views = {

        },
        version = 1
)
@TypeConverters({
        DateConverter.class,
        ListConverter.class
})
public  abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProductDao productDao();

}
