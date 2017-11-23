package lmnp.wirtualnaapteczka.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class AddNotesOnClickListener implements View.OnClickListener {

    private AddActivity addActivity;
    private Medicine medicine;

    public AddNotesOnClickListener(AddActivity addActivity, Medicine medicine) {
        this.addActivity = addActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(addActivity);

        dialog.setTitle(R.string.notes);

        final EditText input = new EditText(addActivity);
        dialog.setView(input);

        if (!TextUtils.isEmpty(medicine.getUserNotes())) {
            input.setText(medicine.getUserNotes());
        }

        dialog.setPositiveButton(R.string.save_btn_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                medicine.setUserNotes(input.getText().toString());
            }
        });

        dialog.setNegativeButton(R.string.back_btn_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        dialog.show();
    }
}
