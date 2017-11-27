package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.Pharmacy;
import lmnp.wirtualnaapteczka.data.entities.User;

import java.util.List;

public interface DbService {
    void createUser(User user);
    void deleteUser(String userId);
    void deleteUser(User user);
    User findUserById(String userId);
    List<User> findUsersByIds(List<String> userIds);

    void createPharmacy(String userId, Pharmacy pharmacy);
    Pharmacy findPharmacyById(String pharmacyId);

    void saveOrUpdateMedicine(String userId, Medicine medicine);
    void saveMedicine(String userId, Medicine medicine);
    void updateMedicine(String userId, Medicine medicine);
    void deleteMedicine(String userId, String medicineId);
    void deleteMedicine(String userId, Medicine medicine);
    Medicine findMedicineById(String userId, String medicineId);
    List<Medicine> findAllMedicinesByUserId(String userId);
    List<Medicine> findRecentlyEditedMedicines(String userId, int resultsLimit);
}
