package lmnp.wirtualnaapteczka.listeners.familyactivity;

import android.view.View;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;

public class AddNewContactOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        AlertDialogPreparator.showSendFamilyMemberInvitationDialog(v.getContext());
    }
}
