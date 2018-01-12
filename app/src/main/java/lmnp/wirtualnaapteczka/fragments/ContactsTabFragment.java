package lmnp.wirtualnaapteczka.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyContactArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.listeners.familyactivity.AddNewContactOnClickListener;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fragment for managing Contacts tab.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class ContactsTabFragment extends Fragment {
    private ListView contactsListView;
    private FloatingActionButton addNewContactBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_tab_contacts, container, false);

        contactsListView = (ListView) view.findViewById(R.id.contacts_list_view);
        contactsListView.setEmptyView(view.findViewById(R.id.contacts_list_empty));

        addNewContactBtn = (FloatingActionButton) view.findViewById(R.id.add_new_contact_btn);
        addNewContactBtn.setOnClickListener(new AddNewContactOnClickListener());

        initializeFirebaseListeners();

        return view;
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        DatabaseReference currentUserReference = firebaseDatabase.getReference(FirebaseConstants.USER_FAMILY).child(currentUser.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() != null) {
                    GenericTypeIndicator<Map<String, FamilyMember>> stringToFamilyMemberMapGenericType = new GenericTypeIndicator<Map<String, FamilyMember>>() {
                    };
                    Map<String, FamilyMember> userIdToFamilyMemberMap = dataSnapshot.getValue(stringToFamilyMemberMapGenericType);

                    List<FamilyMember> acceptedFamilyMembers = new ArrayList<>();

                    if (userIdToFamilyMemberMap != null) {
                        for (FamilyMember familyMember : userIdToFamilyMemberMap.values()) {
                            if (familyMember.getInvitationStatus() == InvitationStatusEnum.ACCEPTED) {
                                acceptedFamilyMembers.add(familyMember);
                            }
                        }
                    }

                    FamilyContactArrayAdapter familyContactArrayAdapter = new FamilyContactArrayAdapter(getContext(), R.id.contacts_list_view, acceptedFamilyMembers);
                    contactsListView.setAdapter(familyContactArrayAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
