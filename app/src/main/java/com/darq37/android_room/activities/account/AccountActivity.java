package com.darq37.android_room.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darq37.android_room.R;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;

public class AccountActivity extends AppCompatActivity {
    private User user;
    private UserDao userDao;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userDao = RoomConstant.getInstance(this).userDao();
        user = userDao.getByIdSync(getIntent().getStringExtra("LOGIN"));

        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        passwordEdit = findViewById(R.id.newPassword);
        TextView userNameText = findViewById(R.id.userNameView);

        Resources res = getResources();

        String username = String.format(res.getString(R.string.accountLogin), user.getDisplayName());
        userNameText.setText(username);

        changePasswordButton.setOnClickListener(this::changePassword);
    }


    public void changePassword(View view){
        String newPassword = passwordEdit.getText().toString();

        if (isPasswordValid(newPassword)){
            user.setPassword(newPassword);
            userDao.update(user);
            System.out.println(user.getPassword());
            Toast.makeText(this, "Password changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Password change failed", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPasswordValid(String newPassword) {
        if (newPassword.isEmpty()){
            Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.equals(user.getPassword())){
            Toast.makeText(this, "Don't use same password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.length() < 5){
            Toast.makeText(this, "Use at least 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}