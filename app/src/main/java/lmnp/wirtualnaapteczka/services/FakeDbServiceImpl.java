package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.Pharmacy;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.entities.UserPreferences;
import lmnp.wirtualnaapteczka.test.utils.SampleMedicinesBuilder;

import java.util.*;

public class FakeDbServiceImpl implements DbService {

    private List<User> users;
    private List<Pharmacy> pharmacies;

    public static DbService createNewInstance() {
        List<User> users = prepareSampleUsers();
        List<Pharmacy> pharmacies = prepareSamplePhamarcies();

        return new FakeDbServiceImpl(users, pharmacies);
    }

    private FakeDbServiceImpl(List<User> users, List<Pharmacy> pharmacies) {
        this.users = users;
        this.pharmacies = pharmacies;
    }

    @Override
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public void deleteUser(String userId) {
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (currentUser.getId().equals(userId)) {
                users.remove(i);

                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public User findUserById(String userId) {
        User userWithMatchingId = null;

        for (User user : users) {
            if (user.getId().equals(userId)) {
                userWithMatchingId = user;

                break;
            }
        }

        return userWithMatchingId;
    }

    @Override
    public List<User> findUsersByIds(List<String> userIds) {
        List<User> matchingUsers = new ArrayList<>();

        for (User user : users) {
            boolean isMatchingUser = userIds.contains(user.getId());

            if (isMatchingUser) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }

    @Override
    public void createPharmacy(String userId, Pharmacy pharmacy) {
        User user = findUserById(userId);

        if (pharmacy.getId() == null) {
            pharmacy.setId(String.valueOf(new Random().nextInt(1000000000)));
        }

        user.setPharmacyId(pharmacy.getId());

        pharmacies.add(pharmacy);
    }

    @Override
    public Pharmacy findPharmacyById(String pharmacyId) {
        Pharmacy foundPharmacy = null;

        for (Pharmacy pharmacy : pharmacies) {
            if (pharmacy.getId().equals(pharmacyId)) {
                foundPharmacy = pharmacy;
                break;
            }
        }

        return foundPharmacy;
    }

    @Override
    public void saveOrUpdateMedicine(String userId, Medicine medicine) {
        if (medicine.getId() != null) {
            updateMedicine(userId, medicine);
        } else {
            saveMedicine(userId, medicine);
        }
    }

    @Override
    public void saveMedicine(String userId, Medicine medicine) {
        User user = findUserById(userId);

        medicine.setId(String.valueOf(new Random().nextInt(1000000000)));
        medicine.setUpdatedAt(new Date());
        medicine.setCreatedAt(new Date());

        Pharmacy pharmacy = findPharmacyById(user.getPharmacyId());
        pharmacy.getMedicines().add(medicine);
    }

    @Override
    public void updateMedicine(String userId, Medicine medicine) {
        Medicine medicineForUpdate = findMedicineById(userId, medicine.getId());

        medicineForUpdate.setName(medicine.getName());
        medicineForUpdate.setType(medicine.getType());
        medicineForUpdate.setThumbnailUri(medicine.getThumbnailUri());
        medicineForUpdate.setDescription(medicine.getDescription());
        medicineForUpdate.setDueDate(medicine.getDueDate());
        medicineForUpdate.setUserNotes(medicine.getUserNotes());
        medicineForUpdate.setShareWithFriends(medicine.isShareWithFriends());
        medicineForUpdate.setAmount(medicine.getAmount());

        medicineForUpdate.setUpdatedAt(new Date());
    }

    @Override
    public void deleteMedicine(String userId, String medicineId) {
        User medicineOwningUser = findUserById(userId);
        Pharmacy pharmacy = findPharmacyById(medicineOwningUser.getPharmacyId());

        if (pharmacy != null) {
            List<Medicine> userMedicines = pharmacy.getMedicines();

            for (int i = 0; i < userMedicines.size(); i++) {
                Medicine currentMedicine = userMedicines.get(i);

                if (currentMedicine.getId().equals(medicineId)) {
                    userMedicines.remove(i);

                    break;
                }
            }
        }
    }

    @Override
    public void deleteMedicine(String userId, Medicine medicine) {
        User medicineOwningUser = findUserById(userId);

        if (medicineOwningUser != null) {
            Pharmacy pharmacy = findPharmacyById(medicineOwningUser.getPharmacyId());

            if (pharmacy != null) {
                List<Medicine> userMedicines = pharmacy.getMedicines();
                userMedicines.remove(medicine);
            }
        }
    }

    @Override
    public Medicine findMedicineById(String userId, String medicineId) {
        Medicine foundMedicine = null;

        User user = findUserById(userId);

        if (user != null) {
            Pharmacy pharmacy = findPharmacyById(user.getPharmacyId());

            if (pharmacy != null) {
                List<Medicine> medicines = pharmacy.getMedicines();

                for (Medicine medicine : medicines) {
                    if (medicine.getId().equals(medicineId)) {
                        foundMedicine = medicine;
                    }
                }
            }
        }

        return foundMedicine;
    }

    @Override
    public List<Medicine> findAllMedicinesByUserId(String userId) {
        User userById = findUserById(userId);

        Pharmacy userPharmacy = null;

        for (Pharmacy pharmacy : pharmacies) {
            if (pharmacy.getId().equals(userById.getPharmacyId())) {
                userPharmacy = pharmacy;
            }
        }

        List<Medicine> userMedicines = userPharmacy != null ? userPharmacy.getMedicines() : null;

        return userMedicines;
    }

    @Override
    public List<Medicine> findRecentlyEditedMedicines(String userId, int resultsLimit) {


        return null;
    }

    private static List<User> prepareSampleUsers() {
        List<User> users = new ArrayList<>();

        UserPreferences globalPreferencesForTests = new UserPreferences();
        globalPreferencesForTests.setRecentlyUsedMedicinesViewLimit(4);

        User user1 = new User();
        user1.setId("1L");
        user1.setFirstName("Paulina");
        user1.setLastName("Preś");
        user1.setPassword("password");
        user1.setPharmacyId("1L");
        user1.setUserPreferences(globalPreferencesForTests);

        User user2 = new User();
        user2.setId("2L");
        user2.setFirstName("Jowita");
        user2.setLastName("Mielnicka");
        user2.setPassword("password");
        user2.setPharmacyId("2L");
        user2.setUserPreferences(globalPreferencesForTests);

        User user3 = new User();
        user3.setId("3L");
        user3.setFirstName("Przemysław");
        user3.setLastName("Lewkowicz");
        user3.setPassword("password");
        user3.setPharmacyId("3L");
        user3.setUserPreferences(globalPreferencesForTests);

        User user4 = new User();
        user4.setId("4L");
        user4.setFirstName("Sebastian");
        user4.setLastName("Nowak");
        user4.setPassword("password");
        user4.setPharmacyId("4L");
        user4.setUserPreferences(globalPreferencesForTests);

        users.addAll(Arrays.asList(user1, user2, user3, user4));

        return users;
    }

    private static List<Pharmacy> prepareSamplePhamarcies() {
        List<Medicine> medicines1 = SampleMedicinesBuilder.prepareBuilder()
                .addFirstMedicine()
                .build();

        List<Medicine> medicines2 = SampleMedicinesBuilder.prepareBuilder()
                .addFirstMedicine()
                .addSecondMedicine()
                .addThirdMedicine()
                .build();

        List<Medicine> medicines3 = SampleMedicinesBuilder.prepareBuilder()
                .addFourthMedicine()
                .addFifthMedicine()
                .build();

        List<Medicine> medicines4 = SampleMedicinesBuilder.prepareBuilder()
                .addAll()
                .build();

        Pharmacy pharmacy1 = new Pharmacy("1L", medicines1);
        Pharmacy pharmacy2 = new Pharmacy("2L", medicines2);
        Pharmacy pharmacy3 = new Pharmacy("3L", medicines3);
        Pharmacy pharmacy4 = new Pharmacy("4L", medicines4);

        List<Pharmacy> pharmacies = Arrays.asList(pharmacy1, pharmacy2, pharmacy3, pharmacy4);

        return pharmacies;
    }
}
