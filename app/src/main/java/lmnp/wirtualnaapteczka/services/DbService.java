package lmnp.wirtualnaapteczka.services;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.UserBasicTO;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;

public interface DbService {
    void createUserAccountCreatorListenerForGoogleAuth(GoogleSignInAccount acct);

    void createUserDataInFirebase(UserRegistrationTO user);

    void createFamilyMemberInvitationForUser(UserBasicTO userForInvitation);

    void updatePendingFamilyMemberInvitationStatus(UserBasicTO userBasicTO, InvitationStatusEnum statusEnum);

    void updateDefaultComparatorInUserPreferences(SortingComparatorTypeEnum defaultSortingComparatorEnum);

    void updateSearchValueInSession(String searchValue);

    void updateSearchValueFamilyInSession(String searchValue);

    void saveOrUpdateMedicine(Medicine medicine);

    void deleteMedicine(String medicineId);

    void deleteFamilyRelationship(String familyMemberUserId);
}
