package com.darq37.android_room.service;

import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.google.gson.JsonArray;
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
    Call<JsonArray> getUsers();

    @GET("/users/{login}")
    Call<JsonObject> getUser(@Path("login") String login);

    @POST("/users")
    Call<JsonObject> addUser(@Body JsonObject jsonObject);


    /*<---------- Products ----------->*/

    @GET("/products")
    Call<List<JsonObject>> getProducts();

    @GET("/products/{id}")
    Call<JsonObject> getProduct(@Path("id") Long id);

    @POST("/products")
    Call<JsonObject> addProduct(@Body Product product);

    /*<---------- Shopping lists ----------->*/

    @GET("/lists")
    Call<List<JsonObject>> getShoppingLists();

    @GET("/lists/{id}")
    Call<JsonObject> getShoppingList(@Path("id") Long id);

    @POST("/lists")
    Call<JsonObject> addShoppingList(@Body ShoppingList list);

    /*<---------- Shared lists ----------->*/

    @GET("/shared")
    Call<List<JsonObject>> getSharedLists();

    @GET("/shared/{id}")
    Call<JsonObject> getSharedList(@Path("id") Long id);

    @POST("/shared")
    Call<JsonObject> addSharedList(@Body SharedList list);

}
