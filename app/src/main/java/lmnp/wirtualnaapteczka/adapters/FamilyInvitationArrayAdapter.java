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
import lmnp.wirtualnaapteczka.data.dto.UserBasicTO;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.listeners.familyactivity.UpdateInvitationStatusOnClickListener;

import java.util.List;

public class FamilyInvitationArrayAdapter extends ArrayAdapter<FamilyMember> {
    private List<FamilyMember> pendingFamilyMembers;
    private Context context;

    private TextView usernameTextView;
    private TextView emailTextView;

    private ImageButton acceptInvitationBtn;
    private ImageButton rejectInvitationButton;

    public FamilyInvitationArrayAdapter(Context context, int resource, List<FamilyMember> pendingFamilyMembers) {
        super(context, resource, pendingFamilyMembers);

        this.pendingFamilyMembers = pendingFamilyMembers;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_pending_contacts_item, null);

        FamilyMember familyMember = pendingFamilyMembers.get(position);

        initializeViewComponents(view);
        updateComponentsValues(familyMember);

        return view;
    }

    private void initializeViewComponents(View view) {
        usernameTextView = (TextView) view.findViewById(R.id.pending_invitation_username);
        emailTextView = (TextView) view.findViewById(R.id.pending_invitation_email);

        acceptInvitationBtn = (ImageButton) view.findViewById(R.id.accept_member_img_btn);
        rejectInvitationButton = (ImageButton) view.findViewById(R.id.reject_member_img_btn);
    }

    private void updateComponentsValues(FamilyMember familyMember) {
        usernameTextView.setText(familyMember.getUsername());
        emailTextView.setText(familyMember.getEmail());

        UserBasicTO userBasicTO = new UserBasicTO(familyMember);

        acceptInvitationBtn.setOnClickListener(new UpdateInvitationStatusOnClickListener(userBasicTO, InvitationStatusEnum.ACCEPTED));
        rejectInvitationButton.setOnClickListener(new UpdateInvitationStatusOnClickListener(userBasicTO, InvitationStatusEnum.REJECTED));
    }
}
