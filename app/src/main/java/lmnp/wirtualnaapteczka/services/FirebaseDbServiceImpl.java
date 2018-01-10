package lmnp.wirtualnaapteczka.services;

import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                if(!dataSnapshot.exists()) {
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

        Map<String, FamilyMember> familyMembers = new HashMap<>();
        familyMembers.put("wJBfGxvZQGakqVHAYmvanIiCMRy2", new FamilyMember("wJBfGxvZQGakqVHAYmvanIiCMRy2", InvitationStatusEnum.ACCEPTED));

        user.setFamilyMembers(familyMembers);

        userRef.setValue(user);
    }

    @Override
    public void createFamilyMemberInvitationForUser(String userId) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        String currentUserUid = currentUser.getUid();

        DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USERS).child(userId).child(FAMILY_MEMBERS).child(currentUserUid);
        FamilyMember familyMember = new FamilyMember(currentUserUid, InvitationStatusEnum.PENDING);

        currentUserInTargetUserFamilyMembersRef.setValue(familyMember);
    }

    @Override
    public void updatePendingFamilyMemberInvitationStatus(String invitationUserId, InvitationStatusEnum statusEnum) {
        FirebaseUser currentUser = SessionManager.getFirebaseUser();
        String currentUserUid = currentUser.getUid();

        DatabaseReference potentialFamilyMemberRef = firebaseDB.getReference(USERS).child(currentUserUid).child(FAMILY_MEMBERS).child(invitationUserId);
        FamilyMember updatedFamilyMember = new FamilyMember(invitationUserId, statusEnum);

        if (statusEnum == InvitationStatusEnum.ACCEPTED) {
            DatabaseReference currentUserInTargetUserFamilyMembersRef = firebaseDB.getReference(USERS).child(invitationUserId).child(FAMILY_MEMBERS).child(currentUserUid);
            FamilyMember familyMember = new FamilyMember(currentUserUid, InvitationStatusEnum.ACCEPTED);

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
    public User findUserById(String userId) {
        DatabaseReference child = firebaseDB.getReference(USERS).child(userId);
        return null;
    }

    @Override
    public List<User> findUsersByIds(List<String> userIds) {
        return null;
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
    public List<Medicine> findAllMedicinesForCurrentUser() {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        DatabaseReference child = firebaseDB.getReference().child(USERS).child(firebaseUser.getUid()).child(MEDICINES);
        return null;
    }

    @Override
    public List<Medicine> findAllMedicinesByUserId(String userId) {
        return null;
    }
}
