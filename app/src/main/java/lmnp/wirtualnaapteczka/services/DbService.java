package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.data.dto.UserLoginTO;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.Pharmacy;
import lmnp.wirtualnaapteczka.data.entities.User;

import java.util.List;

public interface DbService {
    boolean logInUsingFirebase(UserLoginTO userLoginTO);
    boolean logInUsingFacebook();
    boolean logInUsingGoogle();
    void createUserAccountInFirebase(UserRegistrationTO user);

    void deleteUser(String userId);
    void deleteUser(User user);
    User findUserById(String userId);
    List<User> findUsersByIds(List<String> userIds);

    void createPharmacy(Pharmacy pharmacy);
    Pharmacy findPharmacyById(String pharmacyId);

    void saveOrUpdateMedicine(Medicine medicine);
    void saveMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    void deleteMedicine(String medicineId);
    void deleteMedicine(Medicine medicine);
    Medicine findMedicineById(String medicineId);
    List<Medicine> findAllMedicinesForCurrentUser();
    List<Medicine> findAllMedicinesByUserId(String userId);
}
