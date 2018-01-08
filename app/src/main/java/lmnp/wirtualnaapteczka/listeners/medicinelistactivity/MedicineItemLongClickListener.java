package lmnp.wirtualnaapteczka.listeners.medicinelistactivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.utils.functionalinterfaces.Consumer;

public class MedicineItemLongClickListener implements View.OnLongClickListener {
    private Medicine medicine;
    private Context context;

    public MedicineItemLongClickListener(Medicine medicine, Context context) {
        this.medicine = medicine;
        this.context = context;
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialogPreparator.showDeleteMedicineDialog(context, medicine, null);

        return true;
    }
}
