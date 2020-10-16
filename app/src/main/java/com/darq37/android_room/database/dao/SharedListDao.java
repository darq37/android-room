package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.darq37.android_room.entity.SharedList;

import io.reactivex.Completable;

@Dao
public abstract class SharedListDao  extends GenericDao<SharedList, String>{
    @Override
    @Query("DELETE FROM shared_lists")
    public abstract Completable clearAll();

    @Override
    @Query("DELETE FROM users")
    public abstract void clearAllSync();
}
