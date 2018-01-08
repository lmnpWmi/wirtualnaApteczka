package lmnp.wirtualnaapteczka.data.entities;

import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class UserPreferences {
    private int recentlyUsedMedicinesViewLimit;
    private SortingComparatorTypeEnum defaultSortingComparatorEnum;

    public UserPreferences() {
        this.recentlyUsedMedicinesViewLimit = AppConstants.RECENTLY_USED_MEDICINES_DEFAULT_AMOUNT;
        this.defaultSortingComparatorEnum = SortingComparatorTypeEnum.MODIFIED_DESCENDING;
    }

    public int getRecentlyUsedMedicinesViewLimit() {
        return recentlyUsedMedicinesViewLimit;
    }

    public void setRecentlyUsedMedicinesViewLimit(int recentlyUsedMedicinesViewLimit) {
        this.recentlyUsedMedicinesViewLimit = recentlyUsedMedicinesViewLimit;
    }

    public SortingComparatorTypeEnum getDefaultSortingComparatorEnum() {
        return defaultSortingComparatorEnum;
    }

    public void setDefaultSortingComparatorEnum(SortingComparatorTypeEnum defaultSortingComparatorEnum) {
        this.defaultSortingComparatorEnum = defaultSortingComparatorEnum;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "recentlyUsedMedicinesViewLimit=" + recentlyUsedMedicinesViewLimit +
                ", defaultSortingComparatorEnum=" + defaultSortingComparatorEnum +
                '}';
    }
}
