package lmnp.wirtualnaapteczka.data.dto;

public class UserRegistrationTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatedPassword;

    public UserRegistrationTO(String firstName, String lastName, String email, String password, String repeatedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegistrationTO that = (UserRegistrationTO) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return repeatedPassword != null ? repeatedPassword.equals(that.repeatedPassword) : that.repeatedPassword == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (repeatedPassword != null ? repeatedPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRegistrationTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                '}';
    }
}
