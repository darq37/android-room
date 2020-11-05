package com.darq37.android_room.service;

import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    /*<---------- Users ----------->*/

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users/{login}")
    Call<User> getUser(@Path("login") String login);

    @POST("/users")
    Call<JsonObject> addUser(@Body JsonObject jsonObject);

    /*<---------- Products ----------->*/

    @GET("/products")
    Call<List<Product>> getProducts();

    @GET("/products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @POST("/products")
    Call<Product> addProduct(@Body Product product);

    /*<---------- Shopping lists ----------->*/

    @GET("/lists")
    Call<List<ShoppingList>> getShoppingLists();

    @GET("/lists/{id}")
    Call<ShoppingList> getShoppingList(@Path("id") Long id);

    @POST("/lists")
    Call<ShoppingList> addShoppingList(@Body ShoppingList list);

    /*<---------- Shared lists ----------->*/

    @GET("/shared")
    Call<List<SharedList>> getSharedLists();

    @GET("/shared/{id}")
    Call<SharedList> getSharedList(@Path("id") Long id);

    @POST("/shared")
    Call<SharedList> addSharedList(@Body SharedList list);

}
