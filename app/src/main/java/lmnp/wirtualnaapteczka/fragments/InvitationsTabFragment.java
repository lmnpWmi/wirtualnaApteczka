package lmnp.wirtualnaapteczka.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilyInvitationArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fragment for managing invitations tab.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class InvitationsTabFragment extends Fragment {
    private ListView invitationsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_tab_invitations, container, false);

        invitationsListView = (ListView) view.findViewById(R.id.invitation_contacts_list_view);
        invitationsListView.setEmptyView(view.findViewById(R.id.invitation_contacts_list_empty));

        initializeFirebaseListeners();

        return view;
    }

    private void initializeFirebaseListeners() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = SessionManager.getFirebaseUser();

        initializeFamilyInvitationRequestListener(firebaseDatabase, currentUser);
    }

    private void initializeFamilyInvitationRequestListener(FirebaseDatabase firebaseDatabase, FirebaseUser currentUser) {
        DatabaseReference currentUserReference = firebaseDatabase.getReference(FirebaseConstants.USER_FAMILY).child(currentUser.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() != null) {
                    GenericTypeIndicator<Map<String, FamilyMember>> stringToFamilyMemberMapGenericType = new GenericTypeIndicator<Map<String, FamilyMember>>() {
                    };
                    Map<String, FamilyMember> userIdToFamilyMemberMap = dataSnapshot.getValue(stringToFamilyMemberMapGenericType);

                    List<FamilyMember> pendingFamilyMembers = new ArrayList<>();

                    if (userIdToFamilyMemberMap != null) {
                        for (FamilyMember familyMember : userIdToFamilyMemberMap.values()) {
                            if (familyMember.getInvitationStatus() == InvitationStatusEnum.PENDING) {
                                pendingFamilyMembers.add(familyMember);
                            }
                        }
                    }

                    Context context = getContext();
                    if (context != null) {
                        FamilyInvitationArrayAdapter familyInvitationArrayAdapter = new FamilyInvitationArrayAdapter(getContext(), R.id.invitation_contacts_list_view, pendingFamilyMembers);
                        invitationsListView.setAdapter(familyInvitationArrayAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
