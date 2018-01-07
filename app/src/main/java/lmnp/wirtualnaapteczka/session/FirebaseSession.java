package lmnp.wirtualnaapteczka.session;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseSession {
    private FirebaseAuth firebaseAuth;

    public static FirebaseSession prepareInstance(Context context) {
        FirebaseApp.initializeApp(context);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        return new FirebaseSession(firebaseAuth);
    }

    private FirebaseSession(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }
}
