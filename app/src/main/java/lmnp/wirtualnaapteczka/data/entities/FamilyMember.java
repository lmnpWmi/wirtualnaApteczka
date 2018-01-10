package lmnp.wirtualnaapteczka.data.entities;

import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;

public class FamilyMember {
    private String userId;
    private InvitationStatusEnum invitationStatus;

    public FamilyMember() {
    }

    public FamilyMember(String userId, InvitationStatusEnum invitationStatus) {
        this.userId = userId;
        this.invitationStatus = invitationStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public InvitationStatusEnum getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatusEnum invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMember that = (FamilyMember) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return invitationStatus == that.invitationStatus;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (invitationStatus != null ? invitationStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "userId='" + userId + '\'' +
                ", invitationStatus=" + invitationStatus +
                '}';
    }
}
