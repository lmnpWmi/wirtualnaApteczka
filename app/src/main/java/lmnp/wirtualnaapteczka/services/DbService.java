package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;

import java.util.List;

public interface DbService {
    void createUser(User user);
    void deleteUser(Long userId);
    void deleteUser(User user);
    User findUserById(Long userId);
    List<User> findUsersByIds(List<Long> userIds);

    void saveMedicine(Long userId, Medicine medicine);
    void saveOrUpdateMedicine(Long userId, Medicine medicine);
    void deleteMedicine(Long userId, Long medicineId);
    void deleteMedicine(Long userId, Medicine medicine);
    void updateMedicine(Long userId, Medicine medicine);
    Medicine findMedicineById(Long userId, Long medicineId);
    List<Medicine> findAllMedicinesByUserId(Long userId);
    List<Medicine> findRecentlyEditedMedicines(Long userId, int resultsLimit);
}
