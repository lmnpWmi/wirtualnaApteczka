package lmnp.wirtualnaapteczka.session;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.activity.LauncherActivity;
import lmnp.wirtualnaapteczka.data.dto.UserBasicTO;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
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
    private static User currentUser;
    private static Map<String, User> familyUserMembers = new HashMap<>();
    private static Map<String, User> pendingFamilyInvitations = new HashMap<>();
    private static Map<String, UserBasicTO> emailToUserBasicMap = new HashMap<>();

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

    public static Map<String, User> getFamilyUserMembers() {
        return familyUserMembers;
    }

    public static Map<String, User> getPendingFamilyInvitations() {
        return pendingFamilyInvitations;
    }

    public static Map<String, UserBasicTO> getEmailToUserBasicMap() {
        return emailToUserBasicMap;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearSearchValueInUserSession() {
        dbService.updateSearchValueInSession("");
        dbService.updateSearchValueFamilyInSession("");
    }

    public static void initializeCurrentUserFirebaseListeners() {
        FirebaseUser firebaseUser = getFirebaseUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userRef = database.getReference().child(FirebaseConstants.USERS).child(firebaseUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void initializeFamilyMembersFirebaseListener() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference().child(FirebaseConstants.USERS);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (currentUser != null) {
                    Map<String, FamilyMember> familyMembers = currentUser.getFamilyMembers();
                    Map<String, FamilyMember> userIdToFamilyMemberMap = familyMembers != null ? familyMembers : new HashMap<String, FamilyMember>();

                    Iterable<DataSnapshot> allUsersDataSnapshot = dataSnapshot.getChildren();

                    Map<String, User> acceptedUserIdToUserMap = new HashMap<>();
                    Map<String, User> pendingUserIdToUserMap = new HashMap<>();

                    for (DataSnapshot userDataSnapshot : allUsersDataSnapshot) {
                        User familyUserObj = userDataSnapshot.getValue(User.class);
                        String familyUserId = familyUserObj.getId();
                        UserBasicTO userBasicTO = new UserBasicTO(familyUserObj);

                        emailToUserBasicMap.put(familyUserObj.getEmail(), userBasicTO);

                        FamilyMember familyMember = userIdToFamilyMemberMap.get(familyUserId);

                        if (familyMember != null) {
                            InvitationStatusEnum invitationStatus = familyMember.getInvitationStatus();

                            switch(invitationStatus) {
                                case ACCEPTED:
                                    acceptedUserIdToUserMap.put(familyUserId, familyUserObj);
                                    break;
                                case PENDING:
                                    pendingUserIdToUserMap.put(familyUserId, familyUserObj);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    SessionManager.familyUserMembers = acceptedUserIdToUserMap;
                    pendingFamilyInvitations = pendingUserIdToUserMap;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void closeApplication(AppCompatActivity context) {
        Intent intent = new Intent(context, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.EXIT, true);

        context.startActivity(intent);
        context.finish();
    }
}
