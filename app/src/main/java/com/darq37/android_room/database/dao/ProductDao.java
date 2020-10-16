package com.darq37.android_room.database.dao;

import androidx.room.Dao;

import com.darq37.android_room.entity.Product;

@Dao
public abstract class ProductDao extends GenericDao<Product, Long> {
}
