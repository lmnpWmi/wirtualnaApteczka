package lmnp.wirtualnaapteczka.session;

import com.google.firebase.auth.FirebaseAuth;

public class SessionManager {
    public static FirebaseSession firebaseSession;

    private SessionManager() {
    }

    public static FirebaseSession getFirebaseSession() {
        return firebaseSession;
    }

    public static void setFirebaseSession(FirebaseSession firebaseSession) {
        SessionManager.firebaseSession = firebaseSession;
    }

    public static FirebaseAuth getFirebaseAuth() {
        FirebaseAuth firebaseAuth = firebaseSession.getFirebaseAuth();
        return firebaseAuth;
    }
}
