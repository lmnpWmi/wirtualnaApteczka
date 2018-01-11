package lmnp.wirtualnaapteczka.services;

import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.data.dto.UserBasicTO;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.Date;
import java.util.List;

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
        DatabaseReference userRef = firebaseDB.getReference(USERS).child(currentUser.getUid()).child(FirebaseConstants.EMAIL);

        String displayName = acct.getDisplayName();
        String email = acct.getEmail();
        final UserRegistrationTO userRegistrationTO = new UserRegistrationTO(displayName, email);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    createOrUpdateUserAccountInFirebase(userRegistrationTO);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void createOrUpdateUserAccountInFirebase(UserRegistrationTO userRegistrationTO) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        DatabaseReference userRef = firebaseDB.getReference(USERS).child(currentUser.getUid());

        User user = new User();
        user.setId(currentUser.getUid());
        user.setUsername(userRegistrationTO.getUsername());
        user.setEmail(userRegistrationTO.getEmail());

        userRef.setValue(user);
    }

    @Override
    public void createFamilyMemberInvitationForUser(UserBasicTO userBasicTO) {
        User currentUser = SessionManager.getCurrentUser();

        DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USERS).child(userBasicTO.getId()).child(FAMILY_MEMBERS).child(currentUser.getId());
        FamilyMember familyMember = new FamilyMember(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail(), InvitationStatusEnum.PENDING);

        currentUserInTargetUserFamilyMembersRef.setValue(familyMember);
    }

    @Override
    public void updatePendingFamilyMemberInvitationStatus(UserBasicTO userBasicTO, InvitationStatusEnum statusEnum) {
        User currentUser = SessionManager.getCurrentUser();
        String currentUserId = currentUser.getId();

        DatabaseReference potentialFamilyMemberRef = firebaseDB.getReference(USERS).child(currentUserId).child(FAMILY_MEMBERS).child(userBasicTO.getId());
        FamilyMember updatedFamilyMember = new FamilyMember(userBasicTO.getId(), userBasicTO.getUsername(), userBasicTO.getEmail(), statusEnum);

        if (statusEnum == InvitationStatusEnum.ACCEPTED) {
            DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USERS).child(userBasicTO.getId()).child(FAMILY_MEMBERS).child(currentUserId);
            FamilyMember familyMember = new FamilyMember(currentUserId, currentUser.getUsername(), currentUser.getEmail(), InvitationStatusEnum.ACCEPTED);

            currentUserInTargetUserFamilyMembersRef.setValue(familyMember);
        }

        potentialFamilyMemberRef.setValue(updatedFamilyMember);
    }

    @Override
    public void updateDefaultComparatorInUserPreferences(SortingComparatorTypeEnum defaultSortingComparatorEnum) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USERS).child(currentUser.getUid()).child(USER_PREFERENCES).child(DEFAULT_SORTING_COMPARATOR_ENUM);
        userPreferencesRef.setValue(defaultSortingComparatorEnum);
    }

    @Override
    public void updateSearchValueInSession(String searchValue) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USERS).child(currentUser.getUid()).child(USER_SESSION).child(SEARCH_VALUE);

        userPreferencesRef.setValue(searchValue);
    }

    @Override
    public void updateSearchValueFamilyInSession(String searchValue) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        DatabaseReference userPreferencesRef = firebaseDB.getReference(USERS).child(currentUser.getUid()).child(USER_SESSION).child(SEARCH_VALUE_IN_FAMILY);

        userPreferencesRef.setValue(searchValue);
    }

    @Override
    public void saveOrUpdateMedicine(Medicine medicine) {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        DatabaseReference medicinesRef = firebaseDB.getReference(USERS).child(firebaseUser.getUid()).child(MEDICINES);

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
        DatabaseReference medicineRef = firebaseDB.getReference(USERS).child(currentUser.getUid()).child(MEDICINES).child(medicineId);
        medicineRef.removeValue();
    }

    @Override
    public void deleteFamilyRelationship(String familyMemberUserId) {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        String currentUserId = firebaseUser.getUid();

        DatabaseReference familyMemberRefInCurrentUser = firebaseDB.getReference(USERS).child(currentUserId).child(FAMILY_MEMBERS).child(familyMemberUserId);
        familyMemberRefInCurrentUser.removeValue();


        DatabaseReference currentUserRefInFamilyMemberUser = firebaseDB.getReference(USERS).child(familyMemberUserId).child(FAMILY_MEMBERS).child(currentUserId);
        currentUserRefInFamilyMemberUser.removeValue();
    }
}
