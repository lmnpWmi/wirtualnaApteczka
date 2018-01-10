package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;

import java.util.Date;

/**
 * Common utils for adapters.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
public class AdaptersCommonUtils {
    private Context context;

    public AdaptersCommonUtils(Context context) {
        this.context = context;
    }

    public String prepareAmountText(Integer amount) {
        String amountText = context.getResources()
                .getString(R.string.amount);

        if (amount == null) {
            amount = 0;
        }

        String result = amountText + ": " + amount;
        return result;
    }

    public String prepareCreatedAtText(Date createdAt) {
        String result = context.getResources().getString(R.string.created_at) + ": " + DateUtils.formatDate(createdAt, context);
        return result;
    }

    public String prepareMedicineTypeText(MedicineTypeEnum medicineType) {
        String result = context.getResources().getString(R.string.type) + ": " + MedicineTypeUtils.prepareLocalizedMedicineType(medicineType, context);
        return result;
    }

    public String prepareDueDateText(Date dueDate) {
        String result = context.getResources().getString(R.string.due_date) + ": ";

        if (dueDate != null) {
            result += DateUtils.formatDate(dueDate, context);
        } else {
            result += context.getResources().getString(R.string.not_specified);
        }

        return result;
    }

    public String prepareUsernameText(String username) {
        String result = context.getResources().getString(R.string.username_info) + ": " + username;
        return result;
    }

    public void markFieldIfOverdue(Date dueDate, TextView dueDateTextView) {
        if (dueDate != null) {
            Date now = new Date();

            if (dueDate.before(now)) {
                dueDateTextView.setTextColor(Color.RED);
            }
        }
    }
}
