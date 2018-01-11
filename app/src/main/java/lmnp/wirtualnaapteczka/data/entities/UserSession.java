package lmnp.wirtualnaapteczka.data.entities;

/**
 * Entity for storing any informations that may be relevant during the application usage.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class UserSession {
    private String searchValue;
    private String searchValueInFamily;

    public UserSession() {
        this.searchValue = "";
        this.searchValueInFamily = "";
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValueInFamily() {
        return searchValueInFamily;
    }

    public void setSearchValueInFamily(String searchValueInFamily) {
        this.searchValueInFamily = searchValueInFamily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        if (searchValue != null ? !searchValue.equals(that.searchValue) : that.searchValue != null) return false;
        return searchValueInFamily != null ? searchValueInFamily.equals(that.searchValueInFamily) : that.searchValueInFamily == null;
    }

    @Override
    public int hashCode() {
        int result = searchValue != null ? searchValue.hashCode() : 0;
        result = 31 * result + (searchValueInFamily != null ? searchValueInFamily.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "searchValue='" + searchValue + '\'' +
                ", searchValueInFamily='" + searchValueInFamily + '\'' +
                '}';
    }
}
