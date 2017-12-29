package lmnp.wirtualnaapteczka.comparators;

import lmnp.wirtualnaapteczka.data.entities.Medicine;

import java.util.Comparator;

/**
 * Sorts recently updated/used medicines.
 */
public class MedicineDueDateComparator implements Comparator<Medicine> {
    boolean isAscending;

    public MedicineDueDateComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Medicine medicine1, Medicine medicine2) {
        int result;

        if (medicine1.getDueDate() != null && medicine2.getDueDate() != null) {
            result = medicine1.getDueDate().compareTo(medicine2.getDueDate());
        } else if (medicine1.getDueDate() == null || medicine2.getDueDate() == null) {
            result = 0;
        } else if (medicine1.getDueDate() != null || medicine2.getDueDate() == null) {
            result = 1;
        } else {
            result = -1;
        }

        if (!isAscending) {
            result *= -1;
        }

        return result;
    }
}
