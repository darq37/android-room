package com.darq37.android_room.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;
import com.darq37.android_room.service.ApiService;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerLogin, registerName, registerPassword, registerRepeatPassword;
    private Button signUpButton;
    private final static int MIN_PASSWORD_LENGTH = 5;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewInitializations();
        userDao = RoomConstant.getInstance(getApplicationContext()).userDao();
        signUpButton.setOnClickListener(this::performSignUp);

    }

    void viewInitializations() {
        registerLogin = findViewById(R.id.register_login);
        registerName = findViewById(R.id.register_display_name);
        registerPassword = findViewById(R.id.register_password);
        registerRepeatPassword = findViewById(R.id.register_repeat_password);
        signUpButton = findViewById(R.id.bt_register);
    }

    boolean validateInput() {
        if (registerLogin.getText().toString().equals("")) {
            registerLogin.setError("Please Enter First Name");
            return false;
        }
        if (registerName.getText().toString().equals("")) {
            registerName.setError("Please Enter Last Name");
            return false;
        }
        if (registerPassword.getText().toString().equals("")) {
            registerPassword.setError("Please Enter Password");
            return false;
        }
        if (registerRepeatPassword.getText().toString().equals("")) {
            registerRepeatPassword.setError("Please Enter Repeat Password");
            return false;
        }

        if (registerPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            registerPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        if (!registerPassword.getText().toString().equals(registerRepeatPassword.getText().toString())) {
            registerRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }

    public void performSignUp(View v) {
        if (validateInput()) {

            String login = registerLogin.getText().toString();
            String displayName = registerName.getText().toString();
            String password = registerPassword.getText().toString();
            Date created = new Date();
            Date edited = new Date();

            User user = new User();
            user.setLogin(login);
            user.setDisplayName(displayName);
            user.setPassword(password);
            user.setCreationDate(created);
            user.setModificationDate(edited);


            ApiService service = new ApiService(getApplicationContext());

            service.createUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();


            userDao.insert(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();


            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();

        }
    }
}