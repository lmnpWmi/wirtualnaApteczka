package lmnp.wirtualnaapteczka.listeners.medicinelistactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.SessionManager;

public class MedicineItemLongClickListener implements View.OnLongClickListener {
    private Medicine medicine;
    private Context context;

    public MedicineItemLongClickListener(Medicine medicine, Context context) {
        this.medicine = medicine;
        this.context = context;
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                context);
        alert.setTitle(context.getResources().getString(R.string.delete_medicine));
        alert.setMessage(context.getResources().getString(R.string.delete_medicine_msg));
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbService dbService = SessionManager.getDbService();
                dbService.deleteMedicine(SessionManager.getCurrentUser().getId(), medicine.getId());

                Activity activity = (Activity) context;
                activity.recreate();
            }
        });

        alert.setNegativeButton(R.string.no, null);

        alert.show();

        return true;
    }
}
