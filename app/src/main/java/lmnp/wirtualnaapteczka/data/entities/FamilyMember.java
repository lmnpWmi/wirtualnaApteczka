package lmnp.wirtualnaapteczka.data.entities;

import lmnp.wirtualnaapteczka.data.enums.InvitationStatusEnum;

public class FamilyMember {
    private String userId;
    private String username;
    private String email;
    private InvitationStatusEnum invitationStatus;

    public FamilyMember() {
    }

    public FamilyMember(String userId, String username, String email, InvitationStatusEnum invitationStatus) {
        this.userId = userId;
        this.username = username;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMember that = (FamilyMember) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return invitationStatus == that.invitationStatus;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (invitationStatus != null ? invitationStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", invitationStatus=" + invitationStatus +
                '}';
    }
}
