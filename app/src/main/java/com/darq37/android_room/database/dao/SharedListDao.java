package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.darq37.android_room.database.crossrefs.ListWithProducts;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class SharedListDao extends GenericDao<SharedList, String> {

    @Override
    @Query("DELETE FROM shared_lists")
    public abstract Completable clearAll();

    @Override
    @Query("DELETE FROM shared_lists")
    public abstract void clearAllSync();

    @Query("SELECT * FROM shared_lists")
    public abstract Single<List<ShoppingList>> getAll();

    @Query("SELECT * FROM shared_lists")
    public abstract List<SharedList> getAllSync();

    @Query("SELECT * FROM shared_lists WHERE user_id = :login ")
    public abstract List<SharedList> getAllForUserSync(String login);
}
