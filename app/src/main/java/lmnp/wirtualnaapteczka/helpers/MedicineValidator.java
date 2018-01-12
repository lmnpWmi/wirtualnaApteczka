package lmnp.wirtualnaapteczka.helpers;

import android.content.res.Resources;
import android.text.TextUtils;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineValidationResultTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

/**
 * Validates if Medicine meets all necessary requirements to be saved.
 *
 * @author Sebastian Nowak
 * @createdAt 12.01.2018
 */
public class MedicineValidator {
    private MedicineValidator() {
    }

    public static MedicineValidationResultTO validateMedicine(Medicine medicine, Resources resources) {
        MedicineValidationResultTO validationResult = new MedicineValidationResultTO();

        if (TextUtils.isEmpty(medicine.getName())) {
            validationResult.addErrorMessage(resources.getString(R.string.name_cannot_be_empty_msg));
        }

        if (medicine.getType() == null) {
            validationResult.addErrorMessage(resources.getString(R.string.amount_cannot_be_empty_msg));
        }

        if (medicine.getAmount() == null) {
            validationResult.addErrorMessage(resources.getString(R.string.amount_cannot_be_empty_msg));
        }

        return validationResult;
    }
}
