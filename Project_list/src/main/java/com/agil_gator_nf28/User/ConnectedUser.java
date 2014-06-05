package com.agil_gator_nf28.User;

/**
 * Created by Nicolas on 04/06/14.
 */
public class ConnectedUser {

    private static ConnectedUser instance = null;
    private User connectedUser;

    public static ConnectedUser getInstance() {
        if (instance == null) {
            instance = new ConnectedUser();
        }
        return instance;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
}
