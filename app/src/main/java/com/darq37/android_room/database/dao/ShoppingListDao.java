package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.darq37.android_room.database.crossrefs.ListWithProducts;
import com.darq37.android_room.entity.ShoppingList;

import java.util.List;

@Dao
public abstract class ShoppingListDao extends GenericDao<ShoppingList, Long> {



    @Transaction
    @Query("SELECT * FROM shopping_lists WHERE list_id = :id")
    public abstract List<ListWithProducts> loadListWithProducts(Long id);
}
