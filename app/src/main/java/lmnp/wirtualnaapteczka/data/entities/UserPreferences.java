package lmnp.wirtualnaapteczka.data.entities;

public class UserPreferences {
    private int recentlyUsedMedicinesViewLimit;

    public UserPreferences() {
    }

    public int getRecentlyUsedMedicinesViewLimit() {
        return recentlyUsedMedicinesViewLimit;
    }

    public void setRecentlyUsedMedicinesViewLimit(int recentlyUsedMedicinesViewLimit) {
        this.recentlyUsedMedicinesViewLimit = recentlyUsedMedicinesViewLimit;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "recentlyUsedMedicinesViewLimit=" + recentlyUsedMedicinesViewLimit +
                '}';
    }
}
