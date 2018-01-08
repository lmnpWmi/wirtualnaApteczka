package lmnp.wirtualnaapteczka.comparators;

import android.text.TextUtils;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.exceptions.IncorrectMedicineDataException;

import java.util.Comparator;

/**
 * Sorts recently updated/used medicines.
 */
public class MedicineNameComparator implements Comparator<Medicine> {
    boolean isAscending;

    public MedicineNameComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Medicine medicine1, Medicine medicine2) {
        if (TextUtils.isEmpty(medicine1.getName()) || TextUtils.isEmpty(medicine2.getName())) {
            throw new IncorrectMedicineDataException("Name field in Medicine cannot be empty!");
        }

        int result;

        if (isAscending) {
            result = medicine1.getName().compareToIgnoreCase(medicine2.getName());
        }
        else {
            result = -1 * medicine1.getName().compareToIgnoreCase(medicine2.getName());
        }

        return result;
    }
}
