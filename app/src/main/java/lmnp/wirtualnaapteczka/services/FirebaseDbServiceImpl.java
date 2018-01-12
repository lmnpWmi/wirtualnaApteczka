package lmnp.wirtualnaapteczka.services;

import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.data.entities.*;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.Date;

import static lmnp.wirtualnaapteczka.utils.FirebaseConstants.*;

/**
 * Service for interacting with Firebase DB.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class FirebaseDbServiceImpl implements DbService {
    private FirebaseDatabase firebaseDB;

    public static DbService createNewInstance() {
        FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();

        return new FirebaseDbServiceImpl(firebaseDB);
    }

    public FirebaseDbServiceImpl(FirebaseDatabase firebaseDB) {
        this.firebaseDB = firebaseDB;
    }

    public void createUserAccountCreatorListenerForGoogleAuth(GoogleSignInAccount acct) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userRef = firebaseDB.getReference().child(USER_DATA).child(currentUser.getUid()).child(FirebaseConstants.EMAIL);

        String displayName = acct.getDisplayName();
        String email = acct.getEmail();
        final UserRegistrationTO userRegistrationTO = new UserRegistrationTO(displayName, email);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    createUserDataInFirebase(userRegistrationTO);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void createUserDataInFirebase(UserRegistrationTO userRegistrationTO) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        UserBasicTO userBasicTO = new UserBasicTO(currentUser.getUid(), userRegistrationTO.getUsername(), userRegistrationTO.getEmail());

        DatabaseReference userRef = firebaseDB.getReference(USER_DATA).child(currentUser.getUid());
        userRef.setValue(userBasicTO);

        DatabaseReference userPreferencesRef = firebaseDB.getReference(USER_PREFERENCES).child(currentUser.getUid());
        userPreferencesRef.setValue(new UserPreferences());

        DatabaseReference userSessionRef = firebaseDB.getReference(USER_SESSION).child(currentUser.getUid());
        userSessionRef.setValue(new UserSession());
    }

    @Override
    public void createFamilyMemberInvitationForUser(UserBasicTO userForInvitation) {
        UserBasicTO currentUserData = SessionManager.getCurrentUserData();

        DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USER_FAMILY).child(userForInvitation.getId()).child(currentUserData.getId());
        FamilyMember familyMember = new FamilyMember(currentUserData.getId(), currentUserData.getUsername(), currentUserData.getEmail(), InvitationStatusEnum.PENDING);

        currentUserInTargetUserFamilyMembersRef.setValue(familyMember);
    }

    @Override
    public void updatePendingFamilyMemberInvitationStatus(UserBasicTO friendUserForUpdating, InvitationStatusEnum statusEnum) {
        UserBasicTO currentUserData = SessionManager.getCurrentUserData();
        String currentUserId = currentUserData.getId();

        DatabaseReference potentialFamilyMemberRef = firebaseDB.getReference(USER_FAMILY).child(currentUserId).child(friendUserForUpdating.getId());
        FamilyMember updatedFamilyMember = new FamilyMember(friendUserForUpdating.getId(), friendUserForUpdating.getUsername(), friendUserForUpdating.getEmail(), statusEnum);

        if (statusEnum == InvitationStatusEnum.ACCEPTED) {
            DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USER_FAMILY).child(friendUserForUpdating.getId()).child(currentUserId);
            FamilyMember familyMember = new FamilyMember(currentUserId, currentUserData.getUsername(), currentUserData.getEmail(), InvitationStatusEnum.ACCEPTED);

            currentUserInTargetUserFamilyMembersRef.setValue(familyMember);
        }

        potentialFamilyMemberRef.setValue(updatedFamilyMember);
    }

    @Override
    public void updateDefaultComparatorInUserPreferences(SortingComparatorTypeEnum defaultSortingComparatorEnum) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USER_PREFERENCES).child(currentUser.getUid()).child(DEFAULT_SORTING_COMPARATOR_ENUM);
        userPreferencesRef.setValue(defaultSortingComparatorEnum);
    }

    @Override
    public void updateSearchValueInSession(String searchValue) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USER_SESSION).child(currentUser.getUid()).child(SEARCH_VALUE);

        userPreferencesRef.setValue(searchValue);
    }

    @Override
    public void updateSearchValueFamilyInSession(String searchValue) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USER_SESSION).child(currentUser.getUid()).child(SEARCH_VALUE_IN_FAMILY);

        userPreferencesRef.setValue(searchValue);
    }

    @Override
    public void saveOrUpdateMedicine(Medicine medicine) {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        DatabaseReference medicinesRef = firebaseDB.getReference(USER_MEDICINES).child(firebaseUser.getUid());

        if (TextUtils.isEmpty(medicine.getId())) {
            String medicineId = medicinesRef.push().getKey();
            medicine.setId(medicineId);
            medicine.setCreatedAt(new Date());
        }

        medicine.setUpdatedAt(new Date());

        medicinesRef.child(medicine.getId()).setValue(medicine);
    }

    @Override
    public void deleteMedicine(String medicineId) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference medicineRef = firebaseDB.getReference(USER_MEDICINES).child(currentUser.getUid()).child(medicineId);
        medicineRef.removeValue();
    }

    @Override
    public void deleteFamilyRelationship(String familyMemberUserId) {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        String currentUserId = firebaseUser.getUid();

        DatabaseReference familyMemberStatusRefInCurrentUser = firebaseDB.getReference(USER_FAMILY).child(currentUserId).child(familyMemberUserId).child(INVITATION_STATUS);
        familyMemberStatusRefInCurrentUser.setValue(InvitationStatusEnum.DELETED);

        DatabaseReference currentUserStatusRefInFamilyMemberUser = firebaseDB.getReference(USER_FAMILY).child(familyMemberUserId).child(currentUserId).child(INVITATION_STATUS);
        currentUserStatusRefInFamilyMemberUser.setValue(InvitationStatusEnum.DELETED);
    }
}
