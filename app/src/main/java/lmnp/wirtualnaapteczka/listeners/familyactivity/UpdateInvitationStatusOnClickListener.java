package lmnp.wirtualnaapteczka.listeners.familyactivity;

import android.view.View;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class UpdateInvitationStatusOnClickListener implements View.OnClickListener {
    private String familyMemberUserId;
    private InvitationStatusEnum invitationStatus;

    public UpdateInvitationStatusOnClickListener(String familyMemberUserId, InvitationStatusEnum invitationStatus) {
        this.familyMemberUserId = familyMemberUserId;
        this.invitationStatus = invitationStatus;
    }

    @Override
    public void onClick(View v) {
        DbService dbService = SessionManager.getDbService();
        dbService.updatePendingFamilyMemberInvitationStatus(familyMemberUserId, invitationStatus);
    }
}
