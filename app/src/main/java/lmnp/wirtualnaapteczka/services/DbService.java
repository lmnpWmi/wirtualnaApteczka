package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;

import java.util.List;

public interface DbService {
    void createUserAccountInFirebase(UserRegistrationTO user);

    void updateDefaultComparatorInUserPreferences(SortingComparatorTypeEnum defaultSortingComparatorEnum);

    void updateSearchValueInSession(String searchValue);

    User findUserById(String userId);

    List<User> findUsersByIds(List<String> userIds);

    void saveOrUpdateMedicine(Medicine medicine);

    void deleteMedicine(String medicineId);

    List<Medicine> findAllMedicinesForCurrentUser();

    List<Medicine> findAllMedicinesByUserId(String userId);
}
