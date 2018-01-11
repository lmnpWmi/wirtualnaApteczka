package lmnp.wirtualnaapteczka.services;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import lmnp.wirtualnaapteczka.data.dto.UserBasicTO;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;

import java.util.List;

public interface DbService {
    void createUserAccountCreatorListenerForGoogleAuth(GoogleSignInAccount acct);

    void createOrUpdateUserAccountInFirebase(UserRegistrationTO user);

    void createFamilyMemberInvitationForUser(UserBasicTO userId);

    void updatePendingFamilyMemberInvitationStatus(UserBasicTO userBasicTO, InvitationStatusEnum statusEnum);

    void updateDefaultComparatorInUserPreferences(SortingComparatorTypeEnum defaultSortingComparatorEnum);

    void updateSearchValueInSession(String searchValue);

    void updateSearchValueFamilyInSession(String searchValue);

    void saveOrUpdateMedicine(Medicine medicine);

    void deleteMedicine(String medicineId);

    void deleteFamilyRelationship(String familyMemberUserId);
}
