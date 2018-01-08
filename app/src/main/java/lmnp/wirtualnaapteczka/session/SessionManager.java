package lmnp.wirtualnaapteczka.session;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import lmnp.wirtualnaapteczka.activity.LauncherActivity;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.AppConstants;

/**
 * Manager for session.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class SessionManager {
    private static FirebaseAuth firebaseAuth;
    private static DbService dbService;

    private SessionManager() {
    }

    public static DbService getDbService() {
        return dbService;
    }

    public static void setDbService(DbService dbService) {
        SessionManager.dbService = dbService;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        SessionManager.firebaseAuth = firebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser;
    }

    public static void clearSearchValueInUserSession() {
        dbService.updateSearchValueInSession("");
    }

    public static void closeApplication(AppCompatActivity context) {
        Intent intent = new Intent(context, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.EXIT, true);

        context.startActivity(intent);
        context.finish();
    }
}
