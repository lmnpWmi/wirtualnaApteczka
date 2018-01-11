package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.FamilyMember;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;

import java.util.List;

public class FamilyContactArrayAdapter extends ArrayAdapter<FamilyMember> {
    private List<FamilyMember> familyMembers;
    private Context context;

    private TextView familyMemberUsernameTextView;
    private TextView familyMemberEmailTextView;

    private ImageView deleteFamilyMemberBtn;

    public FamilyContactArrayAdapter(Context context, int resource, List<FamilyMember> familyMembers) {
        super(context, resource, familyMembers);
        this.familyMembers = familyMembers;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_family_contact_item, null);

        FamilyMember currentFamilyMember = familyMembers.get(position);

        initializeViewComponents(view);
        updateComponentsValues(currentFamilyMember);

        return view;
    }

    private void updateComponentsValues(final FamilyMember currentFamilyMember) {
        String email = currentFamilyMember.getEmail();
        String username = currentFamilyMember.getUsername();

        familyMemberEmailTextView.setText(email);
        familyMemberUsernameTextView.setText(username);

        deleteFamilyMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogPreparator.showDeleteFamilyMemberDialog(getContext(), currentFamilyMember.getUserId());
            }
        });
    }

    private void initializeViewComponents(View view) {
        familyMemberUsernameTextView = (TextView) view.findViewById(R.id.family_member_username);
        familyMemberEmailTextView = (TextView) view.findViewById(R.id.family_member_email);
        deleteFamilyMemberBtn = (ImageView) view.findViewById(R.id.delete_family_member);
    }
}
