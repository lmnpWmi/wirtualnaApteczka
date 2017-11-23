package lmnp.wirtualnaapteczka.utils;

import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;

public class SessionManager {
    private static User currentUser = FakeDbServiceImpl.createNewInstance()
            .findUserById(4L);

    private SessionManager() {
    }

    public static User getCurrentUser() {
        return SessionManager.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SessionManager.currentUser = currentUser;
    }

    public static DbService obtainDbService() {
        return FakeDbServiceImpl.createNewInstance();
    }
}
