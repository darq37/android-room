package com.darq37.android_room.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    Call<JsonArray> addUsers(@Body JsonArray array);


    /*<---------- Products ----------->*/

    @GET("/products")
    Call<JsonArray> getProducts();

    @GET("/products/{id}")
    Call<JsonObject> getProduct(@Path("id") Long id);

    @POST("/products")
    Call<JsonArray> addProducts(@Body JsonArray array);

    /*<---------- Shopping lists ----------->*/

    @GET("/lists")
    Call<JsonArray> getShoppingLists();

    @GET("/lists/{id}")
    Call<JsonObject> getShoppingList(@Path("id") Long id);

    @POST("/lists")
    Call<JsonArray> addShoppingLists(@Body JsonArray array);

    /*<---------- Shared lists ----------->*/

    @GET("/shared")
    Call<JsonArray> getSharedLists();

    @GET("/shared/{id}")
    Call<JsonObject> getSharedList(@Path("id") Long id);

    @POST("/shared")
    Call<JsonArray> addSharedLists(@Body JsonArray array);

}
