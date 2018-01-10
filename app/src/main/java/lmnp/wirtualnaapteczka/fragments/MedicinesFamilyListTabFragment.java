package lmnp.wirtualnaapteczka.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyMedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Fragment for displaying shared medicines of the family members.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class MedicinesFamilyListTabFragment extends Fragment {
    private ListView medicineFamilyListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_tab_medicines_list, container, false);
        medicineFamilyListView = (ListView) view.findViewById(R.id.medicine_family_list_view);
        medicineFamilyListView.setEmptyView(view.findViewById(R.id.medicine_family_list_empty));

        List<Medicine> sharedMedicinesFromFamilyMembers = prepareSharedMedicinesFromFamilyMembers();
        FamilyMedicineItemArrayAdapter familyMedicineItemArrayAdapter = new FamilyMedicineItemArrayAdapter(getContext(), R.id.medicine_family_list_view, sharedMedicinesFromFamilyMembers);
        medicineFamilyListView.setAdapter(familyMedicineItemArrayAdapter);

        return view;
    }

    private List<Medicine> prepareSharedMedicinesFromFamilyMembers() {
        Map<String, User> userIdToUserObjMap = SessionManager.getFamilyUserMembers();
        List<User> familyUsers = new ArrayList<>(userIdToUserObjMap.values());

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

        Collections.sort(sharedMedicines, SortingComparatorTypeEnum.NAME_ASCENDING.getComparator());

        return sharedMedicines;
    }


}
