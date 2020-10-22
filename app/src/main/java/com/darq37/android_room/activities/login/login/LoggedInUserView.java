package com.darq37.android_room.activities.login.login;

import java.util.Date;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private Date creationDate;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, Date creationDate) {
        this.displayName = displayName;
        this.creationDate = creationDate;
    }

    String getDisplayName() {
        return displayName;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}