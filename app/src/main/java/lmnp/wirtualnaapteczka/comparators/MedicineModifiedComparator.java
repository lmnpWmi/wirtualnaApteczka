package lmnp.wirtualnaapteczka.comparators;

import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.exceptions.IncorrectMedicineDataException;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sorts recently updated/used medicines.
 */
public class MedicineModifiedComparator implements Comparator<Medicine> {
    boolean isAscending;

    public MedicineModifiedComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Medicine medicine1, Medicine medicine2) {
        if (medicine1.getUpdatedAt() == null || medicine2.getUpdatedAt() == null) {
            throw new IncorrectMedicineDataException("UpdatedAt field in Medicine cannot be empty!");
        }

        int result;

        if (isAscending) {
            result = medicine1.getUpdatedAt().compareTo(medicine2.getUpdatedAt());
        }
        else {
            result = -1 * medicine1.getUpdatedAt().compareTo(medicine2.getUpdatedAt());
        }

        return result;
    }
}
