package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Context;
import android.view.View;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;

public class RecentlyUsedMedicineOnClickListener implements View.OnClickListener {
    private Context context;
    private Medicine medicine;

    public RecentlyUsedMedicineOnClickListener(Context context, Medicine medicine) {
        this.context = context;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        AlertDialogPreparator.showEditMedicineAmountDialog(context, medicine);
    }
}
