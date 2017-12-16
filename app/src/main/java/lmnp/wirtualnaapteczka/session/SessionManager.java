package lmnp.wirtualnaapteczka.session;

import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;

public class SessionManager {
    private static String currentUserId = "4L";
    private static DbService dbService = FakeDbServiceImpl.createNewInstance(currentUserId);

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
