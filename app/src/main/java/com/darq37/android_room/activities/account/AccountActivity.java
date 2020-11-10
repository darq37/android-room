package com.darq37.android_room.activities.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darq37.android_room.R;
import com.darq37.android_room.activities.login.LoginActivity;
import com.darq37.android_room.adapters.SharedListAdapter;
import com.darq37.android_room.database.RoomConstant;
import com.darq37.android_room.database.dao.SharedListDao;
import com.darq37.android_room.database.dao.UserDao;
import com.darq37.android_room.entity.User;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountActivity extends AppCompatActivity {
    private UserDao userDao;
    private EditText passwordEdit;
    private TextView userNameText;
    private Button changePasswordButton;
    private RecyclerView sharedListView;
    private SharedListAdapter sharedListAdapter;
    private User loggedUser;
    private SharedListDao sharedListDao;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        initializeDaos();
        initializeViews();
        initializeRecyclerView();

        fillRecyclerView();
        changePasswordButton.setOnClickListener(this::changePassword);
    }

    private void initializeRecyclerView() {
        sharedListAdapter = new SharedListAdapter(Collections.emptyList());
        sharedListView.setAdapter(sharedListAdapter);
    }

    private void initializeDaos() {
        userDao = RoomConstant.getInstance(this).userDao();
        sharedListDao = RoomConstant.getInstance(this).sharedListDao();
    }

    private void fillRecyclerView() {
        String userName = sharedPreferences.getString("user", null);
        Resources res = getResources();
        userDao.getById(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(user ->
                {
                    setLoggedUser(user);
                    String displayName = user.getDisplayName();
                    String name = String.format(res.getString(R.string.accountLogin), displayName);
                    userNameText.setText(name);
                    sharedListDao.getAllForUserDisplayName(user.getDisplayName())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess(sharedLists -> {
                                sharedListAdapter.setLists(sharedLists);
                                sharedListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                sharedListView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                                sharedListView.setAdapter(sharedListAdapter);
                            }).subscribe();

                })
                .subscribe();
    }

    private void initializeViews() {
        sharedListView = findViewById(R.id.shared_list_view);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        passwordEdit = findViewById(R.id.newPassword);
        userNameText = findViewById(R.id.userNameView);
    }

    public void changePassword(View view) {
        String newPassword = passwordEdit.getText().toString();
        if (isPasswordValid(newPassword)) {
            User user = getLoggedUser();
            user.setPassword(newPassword);
            userDao.update(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(this::logout)
                    .subscribe();
        } else {
            Toast.makeText(this, "Password change failed", Toast.LENGTH_LONG).show();
        }
    }

    private void logout() {
        Toast.makeText(this, "Password changed", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isPasswordValid(String newPassword) {
        if (newPassword.isEmpty()) {
            Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.length() < 5) {
            Toast.makeText(this, "Use at least 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}