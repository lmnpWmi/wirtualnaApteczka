package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;

public class RecentlyUsedMedicineOnClickListener implements View.OnClickListener {
    private Context context;
    private Medicine medicine;
    private Class<? extends AppCompatActivity> invokingClass;

    public RecentlyUsedMedicineOnClickListener(Context context, Medicine medicine, Class<? extends AppCompatActivity> invokingClass) {
        this.context = context;
        this.medicine = medicine;
        this.invokingClass = invokingClass;
    }

    @Override
    public void onClick(View v) {
        AlertDialogPreparator.showEditMedicineAmountDialog(context, medicine, invokingClass);
    }
}
