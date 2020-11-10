package com.darq37.android_room.activities.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.register.RegisterActivity;
import com.darq37.android_room.entity.User;
import com.darq37.android_room.service.ApiService;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private EditText passwordEditText;
    private EditText usernameEditText;
    private SharedPreferences sharedPreferences;
    private Button loginButton;
    private Button registerButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        apiService = ApiService.getApiService(getApplicationContext());

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
            apiService.getUser(login).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(jsonObject -> {
                        Gson g = new Gson();
                        User u = g.fromJson(jsonObject, User.class);
                        String password = passwordEditText.getText().toString();
                        if (password.equals(u.getPassword())) {
                            preparePreferences(u);
                            goToMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .doOnError(throwable -> Toast.makeText(getApplicationContext(), "Cannot get user from database", Toast.LENGTH_SHORT).show())
                    .subscribe();
        }
    }

    private void preparePreferences(User user) {
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