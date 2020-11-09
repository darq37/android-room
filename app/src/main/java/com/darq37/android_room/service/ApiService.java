package com.darq37.android_room.service;

import android.content.Context;

import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.SharedList;
import com.darq37.android_room.entity.ShoppingList;
import com.darq37.android_room.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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


        api = retrofit.create(Api.class);

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

    /* -----ASYNC USER------*/

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

    public Maybe<JsonArray> postUsers(List<User> users) {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = postUsersSync(users);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    /* -----SYNC USER------*/

    private JsonObject getUserSync(String login) throws IOException {
        Call<JsonObject> call = api.getUser(login);
        Response<JsonObject> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    private JsonArray getUsersSync() throws IOException {
        Call<JsonArray> call = api.getUsers();
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }


    private JsonArray postUsersSync(List<User> users) throws IOException {
        JsonArray body = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .create()
                .toJsonTree(users).getAsJsonArray();

        Call<JsonArray> call = api.addUsers(body);
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    /* -----ASYNC PRODUCT------*/

    public Maybe<JsonArray> getProducts() {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = getProductsSync();
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Maybe<JsonArray> postProducts(List<Product> products) {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = postProductsSync(products);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    /* -----SYNC PRODUCT------*/

    private JsonArray getProductsSync() throws IOException {
        Call<JsonArray> call = api.getProducts();
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    private JsonArray postProductsSync(List<Product> productList) throws IOException {
        JsonArray body = Product.getGson().toJsonTree(productList).getAsJsonArray();

        /*JsonArray body = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJsonTree(productList).getAsJsonArray();*/

        Call<JsonArray> call = api.addProducts(body);
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }


    /* -----ASYNC SHOPPING LISTS------*/

    public Maybe<JsonArray> getShoppingLists() {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = getShoppingListsSync();
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Maybe<JsonArray> postShoppingLists(List<ShoppingList> shoppingLists) {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = postShoppingListsSync(shoppingLists);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    /* -----SYNC SHOPPING LISTS------*/

    private JsonArray getShoppingListsSync() throws IOException {
        Call<JsonArray> call = api.getShoppingLists();
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    private JsonArray postShoppingListsSync(List<ShoppingList> shoppingLists) throws IOException {
        JsonArray body = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .create()
                .toJsonTree(shoppingLists).getAsJsonArray();

        Call<JsonArray> call = api.addShoppingLists(body);
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    /* -----ASYNC SHARED LISTS------*/

    public Maybe<JsonArray> getSharedLists() {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = getSharedListsSync();
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Maybe<JsonArray> postShared(List<SharedList> sharedLists) {
        return Maybe.create((MaybeOnSubscribe<JsonArray>) emitter -> {
            JsonArray result = postSharedSync(sharedLists);
            if (result == null) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(result);
            }
        }).subscribeOn(Schedulers.io());
    }

    /* -----SYNC SHARED LISTS------*/

    private JsonArray getSharedListsSync() throws IOException {
        Call<JsonArray> call = api.getSharedLists();
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    private JsonArray postSharedSync(List<SharedList> sharedLists) throws IOException {
        JsonArray body = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            String dateFormatAsString = dateFormat.format(src);
                            return new JsonPrimitive(dateFormatAsString);
                        })
                .create()
                .toJsonTree(sharedLists).getAsJsonArray();

        Call<JsonArray> call = api.addSharedLists(body);
        Response<JsonArray> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

}
