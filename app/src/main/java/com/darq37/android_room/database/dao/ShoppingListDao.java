package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.darq37.android_room.entity.ShoppingList;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class ShoppingListDao extends GenericDao<ShoppingList, Long> {

    @Override
    @Query("DELETE FROM shopping_lists")
    public abstract Completable clearAll();

    @Override
    @Query("DELETE FROM shopping_lists")
    public abstract void clearAllSync();

    @Query("SELECT * FROM shopping_lists")
    public abstract Single<List<ShoppingList>> getAll();

    @Query("SELECT * FROM shopping_lists")
    public abstract List<ShoppingList> getAllSync();

    @Query("SELECT * FROM shopping_lists WHERE login = :owner ")
    public abstract List<ShoppingList> getAllForUserSync(String owner);

    @Query("SELECT * FROM shopping_lists WHERE login = :owner")
    public abstract ShoppingList getForUserSync(String owner);

    @Transaction
    @Query("SELECT * FROM shopping_lists WHERE list_id = :id")
    public abstract ShoppingList getByIdSync(long id);

}
