package com.darq37.android_room.service;

import android.content.Context;

import com.darq37.android_room.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private final Api api;
    private Context context;
    private static ApiService INSTANCE;

    private ApiService(Context context) {
        this.context = context;

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .build()
                ).build();


        this.api = retrofit.create(Api.class);

    }

    public static synchronized ApiService getApiService(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ApiService(context);
        }
        return INSTANCE;
    }

    private Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }


    public JsonObject createUserSync(User user) throws IOException {

        JsonElement body = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .create()
                .toJsonTree(user);

        Call<JsonObject> call = api.addUser(body.getAsJsonObject());
        Response<JsonObject> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    public Maybe<JsonObject> createUser(User user) {
        return Maybe.create((MaybeOnSubscribe<JsonObject>) emitter -> {
            JsonObject result = createUserSync(user);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    public JsonObject getUserSync(String login) throws IOException {
        Call<JsonObject> call = api.getUser(login);
        Response<JsonObject> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    public Maybe<JsonObject> getUser(String login) {
        return Maybe.create((MaybeOnSubscribe<JsonObject>) emitter -> {
            JsonObject result = getUserSync(login);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }


    public Maybe<JsonArray> getUsers() {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = getUsersSync();
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    public JsonArray getUsersSync() throws IOException {
        Call<JsonArray> call = api.getUsers();
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }
}
