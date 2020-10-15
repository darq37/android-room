package com.darq37.android_room.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public abstract class GenericDao<T, ID> {

    //<editor-fold desc="asynchronicznie">
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<List<Long>> insert(List<T> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Single<Long> insert(T value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable update(T value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable update(List<T> value);

    @Delete
    public abstract Completable delete(T value);

    @Delete
    public abstract Completable delete(List<T> value);

    public abstract Completable clearAll();

    public Single<Long> deleteAndInsert(T _delete, T _insert) {
        return delete(_delete)
                .subscribeOn(Schedulers.io())
                .andThen(insert(_insert)
                        .subscribeOn(Schedulers.io()));
    }

    public Single<List<Long>> clearAndInsert(List<T> list) {
        return clearAll()
                .subscribeOn(Schedulers.io())
                .andThen(insert(list)
                        .subscribeOn(Schedulers.io()));
    }

    protected abstract Single<List<T>> getAll();

    protected abstract Maybe<T> getById(ID id);

    //synchronicznie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertSync(T value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertSync(List<T> value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateSync(T value);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateSync(List<T> value);

    @Delete
    public abstract void deleteSync(T value);

    public abstract void clearAllSync();

    @Transaction
    public long deleteAndInsertSync(T _delete, T _insert) {
        deleteSync(_delete);
        return insertSync(_insert);
    }

    @Transaction
    public List<Long> clearAndInsertSync(List<T> _insert) {
        clearAllSync();
        return insertSync(_insert);
    }

    protected abstract List<T> getAllSync();

    protected abstract T getByIdSync(ID id);

}
