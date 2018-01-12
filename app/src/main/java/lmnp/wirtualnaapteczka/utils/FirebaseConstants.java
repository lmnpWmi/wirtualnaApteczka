package lmnp.wirtualnaapteczka.utils;

/**
 * Constants for accessing fields in Firebase DB.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public final class FirebaseConstants {
    public static final String DEFAULT_SORTING_COMPARATOR_ENUM = "defaultSortingComparatorEnum";
    public static final String EMAIL = "email";
    public static final String FAMILY_MEMBERS = "familyMembers";
    public static final String INVITATION_STATUS = "invitationStatus";
    public static final String SEARCH_VALUE = "searchValue";
    public static final String SEARCH_VALUE_IN_FAMILY = "searchValueInFamily";
    public static final String USERS = "users";

    // new db structure
    public static final String USER_DATA = "userData";
    public static final String USER_MEDICINES = "userMedicines";
    public static final String USER_PREFERENCES = "userPreferences";
    public static final String USER_SESSION = "userSession";
    public static final String USER_FAMILY = "userFamily";
    public static final String USER_FAMILY_MEDICINES = "userFamilyMedicines";

    private FirebaseConstants() {
    }
}
