package com.darq37.android_room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
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
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("login")
    private String login;

    @ColumnInfo(name = "display_name")
    @SerializedName("displayName")
    private String displayName;

    @ColumnInfo(name = "password")
    @NonNull
    @SerializedName("password")
    private String password;

    @ColumnInfo(name = "user_creation_date")
    @SerializedName("created")
    private Date creationDate = new Date();

    @ColumnInfo(name = "user_modification_date")
    @SerializedName("edited")
    private Date modificationDate = new Date();

    public User() {
        login = null;
        password = null;
    }

    @Ignore
    public User(@NonNull String login, @NonNull String password, String displayName) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
    }

    @Ignore
    public User(@NonNull String login, @NonNull String password) {
        this.login = login;
        this.password = password;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull String login) {
        this.login = login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
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
