package lmnp.wirtualnaapteczka.utils;

import lmnp.wirtualnaapteczka.entities.User;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;

public class SessionManager {
    private static User currentUser = FakeDbServiceImpl.createNewInstance()
            .findUserById(1L);

    private SessionManager() {
    }

    public static User getCurrentUser() {
        return SessionManager.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SessionManager.currentUser = currentUser;
    }
}
