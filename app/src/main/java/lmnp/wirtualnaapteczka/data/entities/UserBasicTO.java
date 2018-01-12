package lmnp.wirtualnaapteczka.data.entities;

/**
 * Transfer object for smaller data of users.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class UserBasicTO {
    private String id;
    private String username;
    private String email;

    public UserBasicTO() {
    }

    public UserBasicTO(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserBasicTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public UserBasicTO(FamilyMember familyMember) {
        this.id = familyMember.getUserId();
        this.username = familyMember.getUsername();
        this.email = familyMember.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        UserBasicTO that = (UserBasicTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserBasicTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
