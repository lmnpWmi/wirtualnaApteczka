package lmnp.wirtualnaapteczka.fragments;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyContactArrayAdapter;
import lmnp.wirtualnaapteczka.adapters.FamilyInvitationArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.listeners.familyactivity.AddNewContactOnClickListener;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jowita on 2018-01-06.
 */

public class ContactsTabFragment extends Fragment {
    private ListView contactsListView;
    private FloatingActionButton addNewContactBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.family_tab_contacts, container, false);

        contactsListView = (ListView) myView.findViewById(R.id.contacts_list_view);
        contactsListView.setEmptyView(myView.findViewById(R.id.contacts_list_empty));

        addNewContactBtn = (FloatingActionButton) myView.findViewById(R.id.add_new_contact_btn);
        addNewContactBtn.setOnClickListener(new AddNewContactOnClickListener());

        initializeFirebaseListeners();

        return myView;
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        DatabaseReference currentUserReference = firebaseDatabase.getReference(FirebaseConstants.USERS).child(currentUser.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Map<String, FamilyMember> userIdToFamilyMemberObjMap = user.getFamilyMembers();
                List<FamilyMember> acceptedFamilyMembers = new ArrayList<>();

                if (userIdToFamilyMemberObjMap != null) {
                    for (FamilyMember familyMember : userIdToFamilyMemberObjMap.values()) {
                        if (familyMember.getInvitationStatus() == InvitationStatusEnum.ACCEPTED) {
                            acceptedFamilyMembers.add(familyMember);
                        }
                    }
                }

                FamilyContactArrayAdapter familyContactArrayAdapter = new FamilyContactArrayAdapter(getContext(), R.id.contacts_list_view, acceptedFamilyMembers);
                contactsListView.setAdapter(familyContactArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
