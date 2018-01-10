package lmnp.wirtualnaapteczka.listeners.familyactivity;

import android.view.View;
import lmnp.wirtualnaapteczka.data.dto.UserBasicTO;
import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class UpdateInvitationStatusOnClickListener implements View.OnClickListener {
    private UserBasicTO userBasicTO;
    private InvitationStatusEnum invitationStatus;

    public UpdateInvitationStatusOnClickListener(UserBasicTO userBasicTO, InvitationStatusEnum invitationStatus) {
        this.userBasicTO = userBasicTO;
        this.invitationStatus = invitationStatus;
    }

    @Override
    public void onClick(View v) {
        DbService dbService = SessionManager.getDbService();
        dbService.updatePendingFamilyMemberInvitationStatus(userBasicTO, invitationStatus);
    }
}
