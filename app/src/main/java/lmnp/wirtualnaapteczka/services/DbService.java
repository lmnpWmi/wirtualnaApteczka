package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.entities.Medicine;
import lmnp.wirtualnaapteczka.entities.User;

import java.util.List;

public interface DbService {
    void createUser(User user);
    void deleteUser(Long userId);
    void deleteUser(User user);
    User findUserById(Long userId);
    List<User> findUsersByIds(List<Long> userIds);

    void addMedicine(Long userId, Medicine medicine);
    void deleteMedicine(Long userId, Long medicineId);
    void deleteMedicine(Long userId, Medicine medicine);
    List<Medicine> findAllMedicinesByUserId(Long userId);
    List<Medicine> findRecentlyEditedMedicines(Long userId, int resultsLimit);
}
