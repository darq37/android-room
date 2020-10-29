package com.darq37.android_room.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public abstract class GenericDao<T, ID>  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insert(T value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insert(List<T> list);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable update(T value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable update(List<T> value);

    @Delete
    public abstract Completable delete(T value);

    public abstract Completable clearAll();

    @Delete
    public abstract Completable delete(List<T> value);

    public abstract void clearAllSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSync(T value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertSync(List<T> value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateSync(T value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateSync(List<T> value);

    @Delete
    public abstract void deleteSync(T value);

    @Delete
    public abstract void deleteSync(List<T> values);

}
