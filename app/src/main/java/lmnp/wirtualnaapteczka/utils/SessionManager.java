package lmnp.wirtualnaapteczka.utils;

import lmnp.wirtualnaapteczka.entities.User;

public class SessionManager {
    private static User user;

    private SessionManager(){
    }

    public static User getUser() {
        return SessionManager.user;
    }

    public static void setCurrentUser(User user) {
        SessionManager.user = user;
    }
}
