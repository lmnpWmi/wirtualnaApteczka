package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AlertDialogPreparator;

public class RecentlyUsedMedicineOnClickListener implements View.OnClickListener {
    private Context context;
    private Medicine medicine;
    private TextView amount;

    public RecentlyUsedMedicineOnClickListener(Context context, Medicine medicine, TextView amount) {
        this.context = context;
        this.medicine = medicine;
        this.amount = amount;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = AlertDialogPreparator.prepareEditMedicineAmountDialog(context, medicine);
        dialog.show();
    }
}
