package lmnp.wirtualnaapteczka.services;

import lmnp.wirtualnaapteczka.entities.Medicine;
import lmnp.wirtualnaapteczka.entities.User;
import lmnp.wirtualnaapteczka.test.utils.SampleMedicinesBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeUserServiceImpl implements UserService {

    private List<User> users;

    public static UserService createNewInstance() {
        List<User> users = prepareSampleUsers();

        return new FakeUserServiceImpl(users);
    }

    private FakeUserServiceImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public void deleteUser(Long userId) {
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
    public User findUserById(Long userId) {
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
    public List<User> findUsersByIds(List<Long> userIds) {
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
    public void addMedicine(Long userId, Medicine medicine) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.getMedicines().add(medicine);
            }
        }
    }

    @Override
    public void deleteMedicine(Long userId, Long medicineId) {
        User medicineOwningUser = findUserById(userId);

        if (medicineOwningUser != null) {
            List<Medicine> userMedicines = medicineOwningUser.getMedicines();

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
    public void deleteMedicine(Long userId, Medicine medicine) {
        User medicineOwningUser = findUserById(userId);

        if (medicineOwningUser != null) {
            List<Medicine> userMedicines = medicineOwningUser.getMedicines();
            userMedicines.remove(medicine);
        }
    }

    @Override
    public List<Medicine> findAllMedicinesByUserId(Long userId) {
        List<Medicine> userMedicines = null;

        User userById = findUserById(userId);

        if (userById != null) {
            userMedicines = userById.getMedicines();
        }

        return userMedicines;
    }

    private static List<User> prepareSampleUsers() {
        List<User> users = new ArrayList<>();

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

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Paulina");
        user1.setLastName("Preś");
        user1.setUsername("palpres");
        user1.setPassword("password");
        user1.setMedicines(medicines1);
        user1.setFriendsIds(Arrays.asList(2L));

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jowita");
        user2.setLastName("Mielnicka");
        user2.setUsername("jowmiel");
        user2.setPassword("password");
        user2.setMedicines(medicines2);
        user2.setFriendsIds(Arrays.asList(1L, 3L));

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Przemysław");
        user3.setLastName("Lewkowicz");
        user3.setUsername("przelew");
        user3.setPassword("password");
        user3.setMedicines(medicines3);
        user3.setFriendsIds(Arrays.asList(2L, 4L));

        User user4 = new User();
        user4.setId(4L);
        user4.setFirstName("Sebastian");
        user4.setLastName("Nowak");
        user4.setUsername("sebnow");
        user4.setPassword("password");
        user4.setMedicines(medicines4);
        user4.setFriendsIds(Arrays.asList(3L));

        users.addAll(Arrays.asList(user1, user2, user3, user4));

        return users;
    }
}
