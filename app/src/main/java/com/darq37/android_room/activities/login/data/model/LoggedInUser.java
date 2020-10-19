package com.darq37.android_room.activities.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String login;
    private String displayName;

    public LoggedInUser(String login, String displayName) {
        this.login = login;
        this.displayName = displayName;
    }

    public String getLogin() {
        return login;
    }

    public String getDisplayName() {
        return displayName;
    }
}