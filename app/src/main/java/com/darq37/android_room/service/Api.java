package com.darq37.android_room.service;

import com.darq37.android_room.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @GET("/users")
    Call<JsonObject> getUsers();

    @GET("/users/{login}")
    Call<JsonObject> getUser(@Path("login") String login);

    @POST("/users")
    Call<JsonObject> addUser(@Body User user);


}
