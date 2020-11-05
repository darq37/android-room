package com.darq37.android_room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.darq37.android_room.entity.Product;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public abstract class ProductDao extends GenericDao<Product, Long> {

    @Query("SELECT * FROM products")
    public abstract List<Product> getAllSync();

    @Override
    @Query("DELETE FROM products")
    public abstract void clearAllSync();

    @Query("SELECT * FROM products WHERE product_id = :id")
    public abstract Product getByIdSync(Long id);

    @Query("SELECT * FROM products WHERE product_name = :name")
    public abstract Product getProductByNameSync(String name);

    @Override
    @Query("DELETE FROM products")
    public abstract Completable clearAll();

    @Query("SELECT * FROM products")
    public abstract Single<List<Product>> getAll();

    @Query("SELECT * FROM products WHERE product_id = :id")
    public abstract Maybe<Product> getById(Long id);

    @Query("SELECT * FROM products WHERE product_name = :name")
    public abstract Maybe<Product> getProductByName(String name);




}
