package lmnp.wirtualnaapteczka.services;

import android.text.TextUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;

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
