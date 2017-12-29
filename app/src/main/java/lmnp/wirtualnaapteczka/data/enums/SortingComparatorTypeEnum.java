package lmnp.wirtualnaapteczka.data.enums;

import lmnp.wirtualnaapteczka.comparators.MedicineCreatedAtComparator;
import lmnp.wirtualnaapteczka.comparators.MedicineDueDateComparator;
import lmnp.wirtualnaapteczka.comparators.MedicineModifiedComparator;
import lmnp.wirtualnaapteczka.comparators.MedicineNameComparator;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

import java.util.Comparator;

/**
 * Enums for all available comparators for sorting medicine list with their actual comparators.
 *
 * @author Sebastian Nowak
 * @createdAt 29.12.2017
 */
public enum SortingComparatorTypeEnum {
    NAME_ASCENDING(new MedicineNameComparator(true)),
    NAME_DESCENDING(new MedicineNameComparator(false)),
    CREATED_ASCENDING(new MedicineCreatedAtComparator(true)),
    CREATED_DESCENDING(new MedicineCreatedAtComparator(false)),
    DUE_DATE_ASCENDING(new MedicineDueDateComparator(true)),
    DUE_DATE_DESCENDING(new MedicineDueDateComparator(false)),
    MODIFIED_ASCENDING(new MedicineModifiedComparator(true)),
    MODIFIED_DESCENDING(new MedicineModifiedComparator(false));

    private Comparator<Medicine> comparator;

    SortingComparatorTypeEnum(Comparator<Medicine> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Medicine> getComparator() {
        return comparator;
    }
}
