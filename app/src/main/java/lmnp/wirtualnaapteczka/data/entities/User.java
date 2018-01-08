package lmnp.wirtualnaapteczka.data.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity for user with his medicines, preferences etc.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4551099958890877311L;

    private String id;

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private Map<String, Medicine> medicines;

    private UserPreferences userPreferences;
    private UserSession userSession;

    public User() {
        this.medicines = new HashMap<>();
        this.userPreferences = new UserPreferences();
        this.userSession = new UserSession();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Map<String, Medicine> medicines) {
        this.medicines = medicines;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (medicines != null ? !medicines.equals(user.medicines) : user.medicines != null) return false;
        if (userPreferences != null ? !userPreferences.equals(user.userPreferences) : user.userPreferences != null)
            return false;
        return userSession != null ? userSession.equals(user.userSession) : user.userSession == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (medicines != null ? medicines.hashCode() : 0);
        result = 31 * result + (userPreferences != null ? userPreferences.hashCode() : 0);
        result = 31 * result + (userSession != null ? userSession.hashCode() : 0);
        return result;
    }
}
