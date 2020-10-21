package com.darq37.android_room.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.darq37.android_room.R;

public class RegisterActivity extends AppCompatActivity {

    EditText registerLogin, registerName, registerPassword, registerRepeatPassword;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewInitializations();
    }

    void viewInitializations() {
        registerLogin = findViewById(R.id.register_login);
        registerName = findViewById(R.id.register_display_name);
        registerPassword = findViewById(R.id.register_password);
        registerRepeatPassword = findViewById(R.id.register_repeat_password);
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

            String firstName = registerLogin.getText().toString();
            String lastName = registerName.getText().toString();
            String password = registerPassword.getText().toString();
            String repeatPassword = registerRepeatPassword.getText().toString();

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

        }
    }
}