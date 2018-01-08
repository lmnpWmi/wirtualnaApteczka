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
    private String username;
    private String email;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (medicines != null ? !medicines.equals(user.medicines) : user.medicines != null) return false;
        if (userPreferences != null ? !userPreferences.equals(user.userPreferences) : user.userPreferences != null)
            return false;
        return userSession != null ? userSession.equals(user.userSession) : user.userSession == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (medicines != null ? medicines.hashCode() : 0);
        result = 31 * result + (userPreferences != null ? userPreferences.hashCode() : 0);
        result = 31 * result + (userSession != null ? userSession.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", medicines=" + medicines +
                ", userPreferences=" + userPreferences +
                ", userSession=" + userSession +
                '}';
    }
}
