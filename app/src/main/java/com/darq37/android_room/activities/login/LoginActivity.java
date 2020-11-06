package com.darq37.android_room.activities.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.register.RegisterActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private EditText passwordEditText;
    private EditText usernameEditText;
    private UserDao userDao;
    private SharedPreferences sharedPreferences;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        userDao = RoomConstant.getInstance(this).userDao();
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        skipLoginIfLogged();

        loginButton.setOnClickListener(this::login);
        registerButton.setOnClickListener(this::goToSignUp);
    }

    private void skipLoginIfLogged() {
        if (sharedPreferences.getBoolean("logged", false)) {
            goToMainActivity();
        }
    }

    private void initializeViews() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.goToSignUpButton);
    }

    public void login(View view) {
        if (isInputValid()) {
            String login = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            userDao.getById(login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(user -> {
                        if (password.equals(user.getPassword())) {
                            preparePreferences(user);
                            goToMainActivity();
                        }
                    })
                    .subscribe();
        }
    }

    private void preparePreferences(User user) {

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged", true);
        editor.putString("user", user.getLogin());
        editor.apply();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean isInputValid() {
        if (usernameEditText.getText().toString().equals("")) {
            usernameEditText.setError("Please enter username");
            return false;
        }
        if (passwordEditText.getText().toString().equals("")) {
            passwordEditText.setError("Please enter password");
            return false;
        }
        int MIN_PASSWORD_LENGTH = 5;
        if (passwordEditText.getText().length() < MIN_PASSWORD_LENGTH) {
            passwordEditText.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }
        return true;
    }

}