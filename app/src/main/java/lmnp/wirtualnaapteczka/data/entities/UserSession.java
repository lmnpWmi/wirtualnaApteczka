package lmnp.wirtualnaapteczka.data.entities;

/**
 * Entity for storing any informations that may be relevant during the application usage.
 *
 * @author Sebastian Nowak
 * @createdAt 08.01.2018
 */
public class UserSession {
    private String searchValue;

    public UserSession() {
        this.searchValue = "";
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        return searchValue != null ? searchValue.equals(that.searchValue) : that.searchValue == null;
    }

    @Override
    public int hashCode() {
        return searchValue != null ? searchValue.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "searchValue='" + searchValue + '\'' +
                '}';
    }
}
