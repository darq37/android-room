package com.darq37.android_room.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.SplashScreenActivity;
import com.darq37.android_room.activities.register.RegisterActivity;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;

public class LoginActivity extends AppCompatActivity {
    private static final int MIN_PASSWORD_LENGTH = 5;
    EditText passwordEditText;
    EditText usernameEditText;
    UserDao userDao;
    public static final String userNAME = "USERNAME_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDao = RoomConstant.getInstance(this).userDao();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.button_login);
        Button registerButton = findViewById(R.id.goToSignUpButton);

        loginButton.setOnClickListener(this::login);
        registerButton.setOnClickListener(this::goToSignUp);
    }

    public void login(View view){
        if (isInputValid()){
            String login = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
           try {
               User user = userDao.getByIdSync(login);
               if (password.equals(user.getPassword())){
                   Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show();
                   Intent intent =  new Intent(this, MainActivity.class);
                   intent.putExtra(userNAME, user.getDisplayName());
                   startActivity(intent);
               }
           }catch (Exception e){
               e.printStackTrace();
           }


        }
    }
    public void goToSignUp(View view){
        Intent intent =  new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean isInputValid(){
        if (usernameEditText.getText().toString().equals("")){
            usernameEditText.setError("Please enter username");
            return false;
        }
        if (passwordEditText.getText().toString().equals("")){
            passwordEditText.setError("Please enter password");
            return false;
        }
        if (passwordEditText.getText().length() < MIN_PASSWORD_LENGTH) {
            passwordEditText.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }
        return true;
    }

    
}