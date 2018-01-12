package lmnp.wirtualnaapteczka.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyMedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.UserSession;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.helpers.MedicineFilter;
import lmnp.wirtualnaapteczka.helpers.SearchMedicineDialogHelper;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fragment for displaying shared medicines of the family members.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class MedicinesFamilyListTabFragment extends Fragment {
    private static final EnumSet<InvitationStatusEnum> VALID_STATUSES_FOR_ATTACHING_LISTENERS = EnumSet.of(InvitationStatusEnum.ACCEPTED, InvitationStatusEnum.DELETED);
    private static Map<DatabaseReference, ValueEventListener> databaseRefToValueListenerMap = new ConcurrentHashMap<>();

    public static SearchMedicineDialogHelper searchMedicineDialog;

    private ListView medicineFamilyListView;
    private FloatingActionButton searchMedicineBtn;
    private Map<String, List<Medicine>> userIdToMedicineListMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_tab_medicines_list, container, false);
        userIdToMedicineListMap = new HashMap<>();

        medicineFamilyListView = (ListView) view.findViewById(R.id.medicine_family_list_view);
        medicineFamilyListView.setEmptyView(view.findViewById(R.id.medicine_family_list_empty));

        searchMedicineBtn = (FloatingActionButton) view.findViewById(R.id.search_medicines_family_btn);
        searchMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMedicineDialog = new SearchMedicineDialogHelper();
                searchMedicineDialog.initializeSearchFamilyMedicineDialog(MedicinesFamilyListTabFragment.this);
            }
        });

        initializeFirebaseListeners();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SessionManager.clearSearchValueInUserSession();
    }

    private void initializeFirebaseListeners() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        String currentUserId = firebaseUser.getUid();

        initializeFriendsMedicineListeners(database, currentUserId);
        initializeSearchMedicineListener(database, currentUserId);
    }

    private void initializeFriendsMedicineListeners(final FirebaseDatabase database, String currentUserId) {
        final DatabaseReference currentUserFamilyRef = database.getReference().child(FirebaseConstants.USER_FAMILY).child(currentUserId);
        currentUserFamilyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userIdToMedicineListMap = new HashMap<>();

                for (DatabaseReference databaseReference : databaseRefToValueListenerMap.keySet()) {
                    ValueEventListener valueEventListener = databaseRefToValueListenerMap.get(databaseReference);
                    databaseReference.removeEventListener(valueEventListener);
                    databaseRefToValueListenerMap.remove(databaseReference);
                }

                List<FamilyMember> acceptedFamilyMembers = extractFamilyMembers(dataSnapshot);

                for (final FamilyMember familyMember : acceptedFamilyMembers) {
                    DatabaseReference familyMemberMedicinesRef = database.getReference().child(FirebaseConstants.USER_MEDICINES).child(familyMember.getUserId());
                    ValueEventListener familyMemberMedicinesListener = familyMemberMedicinesRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Medicine> familyUserSharedMedicines = new ArrayList<>();

                            if (familyMember.getInvitationStatus() == InvitationStatusEnum.ACCEPTED) {
                                GenericTypeIndicator<Map<String, Medicine>> medicineMapTypeIndicator = new GenericTypeIndicator<Map<String, Medicine>>() {
                                };
                                Map<String, Medicine> medicineIdToMedicineMap = dataSnapshot.getValue(medicineMapTypeIndicator);

                                List<Medicine> familyUserMedicines;

                                if (medicineIdToMedicineMap == null) {
                                    familyUserMedicines = new ArrayList<>();
                                } else {
                                    familyUserMedicines = new ArrayList<>(medicineIdToMedicineMap.values());
                                }

                                for (Medicine medicine : familyUserMedicines) {
                                    medicine.setOwnerUsername(familyMember.getUsername());
                                }

                                familyUserSharedMedicines = MedicineFilter.filterOnlySharedMedicines(familyUserMedicines);
                            }
                            userIdToMedicineListMap.put(familyMember.getUserId(), familyUserSharedMedicines);

                            if (getContext() != null) {

                                List<Medicine> medicines = prepareFamilyMembersMedicinesList();

                                UserSession userSession = SessionManager.getUserSession();
                                String searchValue = userSession.getSearchValueInFamily();

                                if (!TextUtils.isEmpty(searchValue)) {
                                    medicines = MedicineFilter.filterMedicines(searchValue, medicines, false);
                                }

                                updateFamilyListView(medicines);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    databaseRefToValueListenerMap.put(familyMemberMedicinesRef, familyMemberMedicinesListener);
                }
            }

            @NonNull
            private List<FamilyMember> extractFamilyMembers(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, FamilyMember>> stringToFamilyMemberMapGenericType = new GenericTypeIndicator<Map<String, FamilyMember>>() {
                };
                Map<String, FamilyMember> userIdToFamilyMemberMap = dataSnapshot.getValue(stringToFamilyMemberMapGenericType);

                List<FamilyMember> validFamilyMemberForAttachingListeners = new ArrayList<>();

                if (userIdToFamilyMemberMap != null) {
                    for (FamilyMember familyMember : userIdToFamilyMemberMap.values()) {
                        if (VALID_STATUSES_FOR_ATTACHING_LISTENERS.contains(familyMember.getInvitationStatus())) {
                            validFamilyMemberForAttachingListeners.add(familyMember);
                        }
                    }
                }
                return validFamilyMemberForAttachingListeners;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeSearchMedicineListener(FirebaseDatabase database, String currentUserId) {
        DatabaseReference sessionSearchValueRef = database.getReference().child(FirebaseConstants.USER_SESSION).child(currentUserId).child(FirebaseConstants.SEARCH_VALUE_IN_FAMILY);
        sessionSearchValueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() != null) {
                    String searchValue = dataSnapshot.getValue(String.class);

                    if (!TextUtils.isEmpty(searchValue)) {
                        List<Medicine> familyMembersMedicines = prepareFamilyMembersMedicinesList();
                        List<Medicine> filteredMedicines = MedicineFilter.filterMedicines(searchValue, familyMembersMedicines, false);

                        updateFamilyListView(filteredMedicines);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateFamilyListView(List<Medicine> familyMembersMedicines) {
        Collections.sort(familyMembersMedicines, SortingComparatorTypeEnum.NAME_ASCENDING.getComparator());
        FamilyMedicineItemArrayAdapter familyMedicineItemArrayAdapter = new FamilyMedicineItemArrayAdapter(getContext(), R.id.medicine_family_list_view, familyMembersMedicines);

        medicineFamilyListView.setAdapter(familyMedicineItemArrayAdapter);
    }

    private List<Medicine> prepareFamilyMembersMedicinesList() {
        Collection<List<Medicine>> collectionOfMedicinesList = userIdToMedicineListMap.values();

        List<Medicine> allSharedMedicines = new ArrayList<>();

        for (List<Medicine> familyMemberMedicines : collectionOfMedicinesList) {
            allSharedMedicines.addAll(familyMemberMedicines);
        }

        return allSharedMedicines;
    }
}
