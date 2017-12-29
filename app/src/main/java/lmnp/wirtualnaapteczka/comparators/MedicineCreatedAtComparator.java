package lmnp.wirtualnaapteczka.comparators;

import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.exceptions.IncorrectMedicineDataException;

import java.util.Comparator;

/**
 * Sorts recently updated/used medicines.
 */
public class MedicineCreatedAtComparator implements Comparator<Medicine> {
    boolean isAscending;

    public MedicineCreatedAtComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Medicine medicine1, Medicine medicine2) {
        if (medicine1.getCreatedAt() == null || medicine2.getCreatedAt() == null) {
            throw new IncorrectMedicineDataException("UpdatedAt field in Medicine cannot be empty!");
        }

        int result;

        if (isAscending) {
            result = medicine1.getCreatedAt().compareTo(medicine2.getCreatedAt());
        }
        else {
            result = -1 * medicine1.getCreatedAt().compareTo(medicine2.getCreatedAt());
        }

        return result;
    }
}
