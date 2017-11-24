package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.SessionManager;

import java.util.Date;

public class RecentlyUsedMedicineOnClickListener implements View.OnClickListener {
    private MainActivity mainActivity;
    private Medicine currentMedicine;
    private TextView amount;

    public RecentlyUsedMedicineOnClickListener(MainActivity mainActivity, Medicine currentMedicine, TextView amount) {
        this.mainActivity = mainActivity;
        this.currentMedicine = currentMedicine;
        this.amount = amount;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mainActivity);
        dialog.setTitle(R.string.edit_amount);

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker, null);

        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.amount_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10000);
        numberPicker.setValue(currentMedicine.getAmount());

        LinearLayout amountPickerPanel = (LinearLayout) view.findViewById(R.id.amount_picker_panel);
        dialog.setView(amountPickerPanel);
        dialog.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int amountValue = numberPicker.getValue();
                currentMedicine.setAmount(amountValue);
                currentMedicine.setUpdatedAt(new Date());
                DbService dbService = SessionManager.obtainDbService();
                dbService.updateMedicine(SessionManager.getCurrentUser().getId(), currentMedicine);

                amount.setText(AdaptersCommonUtils.prepareAmountText(currentMedicine.getAmount(), mainActivity) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), mainActivity));
                mainActivity.initializeRecentlyUsedMedicinesList();
            }
        });

        dialog.setNegativeButton(R.string.cancel_btn, null);

        dialog.show();
    }
}
