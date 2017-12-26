package lmnp.wirtualnaapteczka.listeners.medicinelistactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AlertDialogPreparator;
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
        Consumer invokeAfterMedicineDeleted = new Consumer() {
            @Override
            public void accept(Context context) {
                Activity activity = (Activity) context;
                activity.recreate();
            }
        };

        AlertDialog.Builder alert = AlertDialogPreparator.prepareDeleteMedicineDialog(context, medicine, invokeAfterMedicineDeleted);
        alert.show();

        return true;
    }
}
