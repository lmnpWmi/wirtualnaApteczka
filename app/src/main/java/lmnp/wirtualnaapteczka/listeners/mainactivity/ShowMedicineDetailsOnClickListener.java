package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class ShowMedicineDetailsOnClickListener implements View.OnClickListener {
    private Medicine medicine;
    private AlertDialog alertDialog;
    private Class<? extends AppCompatActivity> invokingClass;

    public ShowMedicineDetailsOnClickListener(Medicine medicine, AlertDialog alertDialog, Class<? extends AppCompatActivity> invokingClass) {
        this.medicine = medicine;
        this.alertDialog = alertDialog;
        this.invokingClass = invokingClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
        intent.putExtra(AppConstants.MEDICINE, medicine);
        intent.putExtra(AppConstants.INVOKING_CLASS, invokingClass);

        v.getContext().startActivity(intent);
        alertDialog.dismiss();
    }
}
