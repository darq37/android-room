package com.darq37.android_room.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.darq37.android_room.MainActivity;
import com.darq37.android_room.R;
import com.darq37.android_room.activities.list.ListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        FloatingActionButton toMainActivity = findViewById(R.id.backToMain);
        EditText passwordEdit = findViewById(R.id.newPassword);
        TextView userNameText = findViewById(R.id.userNameView);
        TextView createdText = findViewById(R.id.creationDateView);

        userNameText.setText("user");
        createdText.setText("2020");


        changePasswordButton.setOnClickListener(this::changePassword);
        toMainActivity.setOnClickListener(this::goToMainActivity);
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void changePassword(View view){
        System.out.println("Password changed");
    }


}