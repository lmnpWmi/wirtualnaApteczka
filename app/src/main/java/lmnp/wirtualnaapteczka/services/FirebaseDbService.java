package lmnp.wirtualnaapteczka.services;

import com.google.firebase.database.FirebaseDatabase;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.Pharmacy;
import lmnp.wirtualnaapteczka.data.entities.User;

import java.util.List;

public class FirebaseDbService implements DbService {

    private static String databaseUrl = "http://google/firebase.babas/";

    private FirebaseDatabase mFirebaseDB;

    public static DbService createNewInstance() {
        return new FirebaseDbService();
    }

    private FirebaseDbService() {
        this.mFirebaseDB = FirebaseDatabase.getInstance();
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User findUserById(String userId) {
        return null;
    }

    @Override
    public List<User> findUsersByIds(List<String> userIds) {
        return null;
    }

    @Override
    public void createPharmacy(String userId, Pharmacy pharmacy) {

    }

    @Override
    public Pharmacy findPharmacyById(String pharmacyId) {
        return null;
    }

    @Override
    public void saveMedicine(String userId, Medicine medicine) {

    }

    @Override
    public void saveOrUpdateMedicine(String userId, Medicine medicine) {

    }

    @Override
    public void deleteMedicine(String userId, String medicineId) {

    }

    @Override
    public void deleteMedicine(String userId, Medicine medicine) {

    }

    @Override
    public void updateMedicine(String userId, Medicine medicine) {

    }

    @Override
    public Medicine findMedicineById(String userId, String medicineId) {
        return null;
    }

    @Override
    public List<Medicine> findAllMedicinesByUserId(String userId) {
        return null;
    }

    @Override
    public List<Medicine> findRecentlyEditedMedicines(String userId, int resultsLimit) {
        return null;
    }

    // W tej metodzie testuj czy wszystko działą
    public static void test() {
        // Utworzyć przykładowego usera, przykładową apteczke, przykładowe leki i testować wywołania serwisu na bazie, tak żeby wykonywały to czego się spodziewamy
        User exampleUser = new User();
        DbService dbService = FirebaseDbService.createNewInstance();
        dbService.createUser(exampleUser);
    }
}
