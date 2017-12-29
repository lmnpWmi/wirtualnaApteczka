package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class ShowMedicineDetailsOnClickListener implements View.OnClickListener {
    private Medicine medicine;
    private AlertDialog alertDialog;

    public ShowMedicineDetailsOnClickListener(Medicine medicine, AlertDialog alertDialog) {
        this.medicine = medicine;
        this.alertDialog = alertDialog;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
        intent.putExtra(AppConstants.MEDICINE, medicine);

        v.getContext().startActivity(intent);
        alertDialog.dismiss();
    }
}
