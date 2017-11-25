package lmnp.wirtualnaapteczka.utils;

import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;

public class SessionManager {
    private static Long currentUserId = 4L;
    private static DbService dbService = FakeDbServiceImpl.createNewInstance();

    private SessionManager() {
    }

    public static User getCurrentUser() {
        return dbService.findUserById(currentUserId);
    }

    public static void setCurrentUser(User currentUser) {
        if (currentUser.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null!");
        }

        currentUserId = currentUser.getId();
    }

    public static DbService getDbService() {
        return dbService;
    }
}
