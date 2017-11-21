package lmnp.wirtualnaapteczka.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class AddMedicineNotesListener implements View.OnClickListener {

    private AddActivity addActivity;
    private Medicine medicine;

    public AddMedicineNotesListener(AddActivity addActivity, Medicine medicine) {
        this.addActivity = addActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(addActivity);

        alert.setTitle(R.string.notes);
        alert.setMessage(R.string.notes_message);

        final EditText input = new EditText(addActivity);
        alert.setView(input);

        if (!TextUtils.isEmpty(medicine.getUserNotes())) {
            input.setText(medicine.getUserNotes());
        }

        alert.setPositiveButton(R.string.save_btn_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                medicine.setUserNotes(input.getText().toString());
            }
        });

        alert.setNegativeButton(R.string.back_btn_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
