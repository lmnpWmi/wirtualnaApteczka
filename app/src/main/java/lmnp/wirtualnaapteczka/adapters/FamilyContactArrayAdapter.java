package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.User;

import java.util.List;

public class FamilyContactArrayAdapter extends ArrayAdapter<User> {
    private List<User> familyMembers;
    private Context context;

    private TextView familyMemberUsernameTextView;
    private TextView familyMemberEmailTextView;

    public FamilyContactArrayAdapter(Context context, int resource, List<User> familyMembers) {
        super(context, resource, familyMembers);
        this.familyMembers = familyMembers;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_family_contact_item, null);

        User currentFamilyMember = familyMembers.get(position);

        initializeViewComponents(view);
        updateComponentsValues(currentFamilyMember);

        return view;
    }

    private void updateComponentsValues(User currentFamilyMember) {
        String email = currentFamilyMember.getEmail();
        String username = currentFamilyMember.getUsername();

        familyMemberEmailTextView.setText(email);
        familyMemberUsernameTextView.setText(username);
    }

    private void initializeViewComponents(View view) {
        familyMemberUsernameTextView = (TextView) view.findViewById(R.id.family_member_username);
        familyMemberEmailTextView = (TextView) view.findViewById(R.id.family_member_email);
    }
}
