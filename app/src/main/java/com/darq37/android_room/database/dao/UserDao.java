package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.darq37.android_room.entity.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public abstract class UserDao extends GenericDao<User, String> {
    @Query("DELETE FROM users")
    public abstract Completable clearAll();

    @Query("DELETE FROM users")
    public abstract void clearAllSync();

    @Query("SELECT * FROM users")
    public abstract Single<List<User>> getAll();

    @Query("SELECT * FROM users")
    public abstract List<User> getAllSync();

    @Query("SELECT * FROM users WHERE login = :login")
    public abstract Maybe<User> getById(String login);

    @Query("SELECT * FROM users WHERE login = :login")
    public abstract User getByIdSync(String login);

}
