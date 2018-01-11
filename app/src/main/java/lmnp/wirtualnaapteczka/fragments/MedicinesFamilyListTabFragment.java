package lmnp.wirtualnaapteczka.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyMedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.entities.UserSession;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.helpers.MedicineFilter;
import lmnp.wirtualnaapteczka.helpers.SearchMedicineDialogHelper;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.*;

/**
 * Fragment for displaying shared medicines of the family members.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class MedicinesFamilyListTabFragment extends Fragment {
    private ListView medicineFamilyListView;
    private FloatingActionButton searchMedicineBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_tab_medicines_list, container, false);
        medicineFamilyListView = (ListView) view.findViewById(R.id.medicine_family_list_view);
        medicineFamilyListView.setEmptyView(view.findViewById(R.id.medicine_family_list_empty));

        searchMedicineBtn = (FloatingActionButton) view.findViewById(R.id.search_medicines_family_btn);
        searchMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMedicineDialogHelper searchMedicineDialogHelper = new SearchMedicineDialogHelper();
                searchMedicineDialogHelper.initializeSearchFamilyMedicineDialog(MedicinesFamilyListTabFragment.this);
            }
        });

        Map<String, User> userIdToUserObjMap = SessionManager.getFamilyUserMembers();
        List<User> familyUsers = new ArrayList<>(userIdToUserObjMap.values());

        List<Medicine> sharedMedicinesFromFamilyMembers = prepareSharedMedicinesFromFamilyMembers(familyUsers);
        FamilyMedicineItemArrayAdapter familyMedicineItemArrayAdapter = new FamilyMedicineItemArrayAdapter(getContext(), R.id.medicine_family_list_view, sharedMedicinesFromFamilyMembers);
        medicineFamilyListView.setAdapter(familyMedicineItemArrayAdapter);

        initializeFirebaseListeners();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SessionManager.clearSearchValueInUserSession();
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference().child(FirebaseConstants.USERS);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = SessionManager.getCurrentUser();

                if (currentUser != null && getContext() != null) {
                    Map<String, FamilyMember> familyMembers = currentUser.getFamilyMembers();
                    Map<String, FamilyMember> userIdToFamilyMemberMap = familyMembers != null ? familyMembers : new HashMap<String, FamilyMember>();

                    Iterable<DataSnapshot> allUsersDataSnapshot = dataSnapshot.getChildren();

                    List<User> familyUsers = new ArrayList<>();

                    for (DataSnapshot userDataSnapshot : allUsersDataSnapshot) {
                        User familyUserObj = userDataSnapshot.getValue(User.class);
                        String familyUserId = familyUserObj.getId();

                        FamilyMember familyMember = userIdToFamilyMemberMap.get(familyUserId);

                        if (familyMember != null) {
                            InvitationStatusEnum invitationStatus = familyMember.getInvitationStatus();

                            if (invitationStatus == InvitationStatusEnum.ACCEPTED) {
                                familyUsers.add(familyUserObj);
                            }
                        }
                    }

                    List<Medicine> sharedMedicines = prepareSharedMedicinesFromFamilyMembers(familyUsers);
                    FamilyMedicineItemArrayAdapter familyMedicineItemArrayAdapter = new FamilyMedicineItemArrayAdapter(getContext(), R.id.medicine_family_list_view, sharedMedicines);
                    medicineFamilyListView.setAdapter(familyMedicineItemArrayAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Medicine> prepareSharedMedicinesFromFamilyMembers(List<User> familyUsers) {
        List<Medicine> sharedMedicines = new ArrayList<>();

        for (User familyUser : familyUsers) {
            Map<String, Medicine> medicineIdToUserMedicinesMap = familyUser.getMedicines();

            if (medicineIdToUserMedicinesMap != null) {
                List<Medicine> familyUserMedicines = new ArrayList<>(medicineIdToUserMedicinesMap.values());

                for (Medicine medicine : familyUserMedicines) {
                    if (medicine.isShareWithFriends()) {
                        medicine.setOwnerUsername(familyUser.getUsername());
                        sharedMedicines.add(medicine);
                    }
                }
            }
        }

        UserSession userSession = SessionManager.getCurrentUser().getUserSession();
        String searchValue = userSession.getSearchValueInFamily();

        if (!TextUtils.isEmpty(searchValue)) {
            sharedMedicines = MedicineFilter.filterMedicines(searchValue, sharedMedicines, false);
        }

        Collections.sort(sharedMedicines, SortingComparatorTypeEnum.NAME_ASCENDING.getComparator());

        return sharedMedicines;
    }
}
