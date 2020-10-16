package com.darq37.android_room.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.darq37.android_room.database.converters.DateConverter;
import com.darq37.android_room.database.converters.ListProductConverter;
import com.darq37.android_room.database.converters.ListStringConverter;
import com.darq37.android_room.database.dao.ProductDao;
import com.darq37.android_room.database.dao.ShoppingListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;


@Database(
        entities = {
                User.class,
                Product.class,
                ShoppingList.class,
        },
        views = {

        },
        version = 1
)
@TypeConverters({
        DateConverter.class,
        ListStringConverter.class,
        ListProductConverter.class
})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ProductDao productDao();

    public abstract ShoppingListDao shoppingListDao();

}
