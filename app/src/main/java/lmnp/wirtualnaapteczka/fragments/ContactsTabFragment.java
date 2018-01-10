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
import lmnp.wirtualnaapteczka.data.entities.User;
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
        addNewContactBtn = (FloatingActionButton) myView.findViewById(R.id.add_new_contact_btn);

        FamilyContactArrayAdapter familyContactArrayAdapter = prepareFamilyContactsAdapter();
        contactsListView.setAdapter(familyContactArrayAdapter);

        addNewContactBtn.setOnClickListener(new AddNewContactOnClickListener());

        initializeFirebaseListeners();

        return myView;
    }

    @NonNull
    private FamilyContactArrayAdapter prepareFamilyContactsAdapter() {
        Map<String, User> familyMembersMap = SessionManager.getFamilyMembers();
        List<User> familyMembers = new ArrayList<>(familyMembersMap.values());

        return new FamilyContactArrayAdapter(this.getContext(), R.id.contacts_list_view, familyMembers);
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        DatabaseReference currentUserReference = firebaseDatabase.getReference(FirebaseConstants.USERS).child(currentUser.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FamilyContactArrayAdapter familyContactArrayAdapter = prepareFamilyContactsAdapter();
                contactsListView.setAdapter(familyContactArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
