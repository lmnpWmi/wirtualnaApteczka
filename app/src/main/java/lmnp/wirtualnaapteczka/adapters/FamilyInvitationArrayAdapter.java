package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.listeners.familyactivity.UpdateInvitationStatusOnClickListener;

import java.util.List;

public class FamilyInvitationArrayAdapter extends ArrayAdapter<User> {
    private List<User> usersWithPendingInvitations;
    private Context context;

    private TextView usernameTextView;
    private TextView emailTextView;

    private ImageButton acceptInvitationBtn;
    private ImageButton rejectInvitationButton;

    public FamilyInvitationArrayAdapter(Context context, int resource, List<User> usersWithPendingInvitations) {
        super(context, resource, usersWithPendingInvitations);

        this.usersWithPendingInvitations = usersWithPendingInvitations;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_pending_contacts_item, null);

        User user = usersWithPendingInvitations.get(position);

        initializeViewComponents(view);
        updateComponentsValues(user);

        return view;
    }

    private void initializeViewComponents(View view) {
        usernameTextView = (TextView) view.findViewById(R.id.pending_invitation_username);
        emailTextView = (TextView) view.findViewById(R.id.pending_invitation_email);

        acceptInvitationBtn = (ImageButton) view.findViewById(R.id.accept_member_img_btn);
        rejectInvitationButton = (ImageButton) view.findViewById(R.id.reject_member_img_btn);
    }

    private void updateComponentsValues(User user) {
        usernameTextView.setText(user.getUsername());
        emailTextView.setText(user.getEmail());

        String familyMemberUserId = user.getId();

        acceptInvitationBtn.setOnClickListener(new UpdateInvitationStatusOnClickListener(familyMemberUserId, InvitationStatusEnum.ACCEPTED));
        rejectInvitationButton.setOnClickListener(new UpdateInvitationStatusOnClickListener(familyMemberUserId, InvitationStatusEnum.REJECTED));
    }
}
