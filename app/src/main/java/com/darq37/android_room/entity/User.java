package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


import java.util.Date;

@Entity(
        tableName = "users",
        indices = {
                @Index(
                        value = {"login"},
                        unique = true
                )
        }
)
public class User {
    @PrimaryKey
    @NonNull
    private String login;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @Ignore
    @NonNull
    private String password;

    @ColumnInfo(name = "user_creation_date")
    private Date creationDate;

    @ColumnInfo(name = "User_modification_date")
    private Date modificationDate;

    public User() {
    }

    @Ignore
    public User(@NonNull String login, String displayName, @NonNull String password) {
        this.login = login;
        this.displayName = displayName;
        this.password = password;
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
