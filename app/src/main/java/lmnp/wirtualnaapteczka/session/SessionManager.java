package lmnp.wirtualnaapteczka.session;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.activity.LauncherActivity;
import lmnp.wirtualnaapteczka.data.entities.UserBasicTO;
import lmnp.wirtualnaapteczka.data.entities.UserPreferences;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for session.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class SessionManager {
    private static FirebaseAuth firebaseAuth;
    private static DbService dbService;
    private static UserBasicTO currentUserData;
    private static UserPreferences userPeferences;
    private static Map<String, UserBasicTO> emailToUserData;

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

    public static UserBasicTO getCurrentUserData() {
        return currentUserData;
    }

    public static Map<String, UserBasicTO> getEmailToUserData() {
        return emailToUserData;
    }

    public static UserPreferences getUserPeferences() {
        return userPeferences;
    }

    public static void clearSearchValueInUserSession() {
        dbService.updateSearchValueInSession("");
        dbService.updateSearchValueFamilyInSession("");
    }

    public static void initializeFirebaseListener() {
        FirebaseUser firebaseUser = getFirebaseUser();
        final String currentUserUid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        initializeUserDataListener(currentUserUid, database);
        initializeUserPreferencesListener(currentUserUid, database);
    }

    public static void closeApplication(AppCompatActivity context) {
        Intent intent = new Intent(context, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.EXIT, true);

        context.startActivity(intent);
        context.finish();
    }

    private static void initializeUserDataListener(final String currentUserUid, FirebaseDatabase database) {
        DatabaseReference userRef = database.getReference().child(FirebaseConstants.USER_DATA);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> usersDataSnapshot = dataSnapshot.getChildren();
                emailToUserData = new HashMap<>();

                for (DataSnapshot userDataSnapshot : usersDataSnapshot) {
                    UserBasicTO userData = userDataSnapshot.getValue(UserBasicTO.class);
                    emailToUserData.put(userData.getEmail(), userData);

                    if (userData.getId().equals(currentUserUid)) {
                        currentUserData = userData;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private static void initializeUserPreferencesListener(String currentUserUid, FirebaseDatabase database) {
        DatabaseReference userPreferencesRef = database.getReference().child(FirebaseConstants.USER_PREFERENCES).child(currentUserUid);
        userPreferencesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userPeferences = dataSnapshot.getValue(UserPreferences.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
