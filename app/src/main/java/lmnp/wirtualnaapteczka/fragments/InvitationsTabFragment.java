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
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jowita on 2018-01-06.
 */

public class InvitationsTabFragment extends Fragment {
    private ListView invitationsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.family_tab_invitations, container, false);

        invitationsListView = (ListView) myView.findViewById(R.id.invitation_contacts_list_view);

        FamilyInvitationArrayAdapter familyInvitationArrayAdapter = prepareFamilyInvitationsArrayAdapter();
        invitationsListView.setAdapter(familyInvitationArrayAdapter);

        initializeFirebaseListeners();

        return myView;
    }

    @NonNull
    private FamilyInvitationArrayAdapter prepareFamilyInvitationsArrayAdapter() {
        Map<String, User> pendingFamilyInvitations = SessionManager.getPendingFamilyInvitations();
        List<User> pendingFamilyUsers = new ArrayList<>(pendingFamilyInvitations.values());

        FamilyInvitationArrayAdapter familyInvitationArrayAdapter = new FamilyInvitationArrayAdapter(this.getContext(), R.id.invitation_contacts_list_view, pendingFamilyUsers);

        return familyInvitationArrayAdapter;
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        DatabaseReference currentUserReference = firebaseDatabase.getReference(FirebaseConstants.USERS).child(currentUser.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FamilyInvitationArrayAdapter familyInvitationArrayAdapter = prepareFamilyInvitationsArrayAdapter();
                invitationsListView.setAdapter(familyInvitationArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
