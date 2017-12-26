package lmnp.wirtualnaapteczka.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.functionalinterfaces.Consumer;

public class AlertDialogPreparator {
    public static AlertDialog.Builder prepareDeleteMedicineDialog(final Context context, final Medicine medicine, final Consumer invokeAfterActionConsumer) {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                context);
        alert.setTitle(context.getResources().getString(R.string.delete_medicine));
        alert.setMessage(context.getResources().getString(R.string.delete_medicine_msg));
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbService dbService = SessionManager.getDbService();
                dbService.deleteMedicine(medicine.getId());

                invokeAfterActionConsumer.accept(context);
            }
        });

        alert.setNegativeButton(R.string.no, null);

        return alert;
    }

    public static AlertDialog.Builder prepareEditMedicineAmountDialog(final Context context, final Medicine medicine) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.edit_amount);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker, null);

        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.amount_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10000);
        numberPicker.setValue(medicine.getAmount());

        LinearLayout amountPickerPanel = (LinearLayout) view.findViewById(R.id.amount_picker_panel);
        dialog.setView(amountPickerPanel);
        dialog.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int amountValue = numberPicker.getValue();
                medicine.setAmount(amountValue);
                DbService dbService = SessionManager.getDbService();
                dbService.updateMedicine(medicine);

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.initializeRecentlyUsedMedicinesList();
            }
        });

        dialog.setNegativeButton(R.string.cancel_btn, null);

        return dialog;
    }
}
