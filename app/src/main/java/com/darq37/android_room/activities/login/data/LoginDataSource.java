package com.darq37.android_room.activities.login.data;

import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.database.room.AppDatabase;
import com.darq37.android_room.entity.User;

import java.io.IOException;

import static com.darq37.android_room.activities.SplashScreenActivity.getContext;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private AppDatabase db = RoomConstant.getInstance(getContext());

    public Result<User> login(String username, String password) {
        UserDao userDao = db.userDao();
        if (username.equals(userDao.getById(username).toString()) && password.equals(userDao.getById(username).blockingGet().getPassword())){
                User user = userDao.getByIdSync(username);
                return new Result.Success<>(user);
        }else return new Result.Error(new IOException("User not found in DB"));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}