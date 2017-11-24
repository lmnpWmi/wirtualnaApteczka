package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class AddNotesOnClickListener implements View.OnClickListener {

    private AddMedicineActivity addMedicineActivity;
    private Medicine medicine;

    public AddNotesOnClickListener(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        this.addMedicineActivity = addMedicineActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(addMedicineActivity);
        dialog.setTitle(R.string.notes);

        final EditText input = new EditText(addMedicineActivity);
        dialog.setView(input);

        if (!TextUtils.isEmpty(medicine.getUserNotes())) {
            input.setText(medicine.getUserNotes());
        }

        dialog.setPositiveButton(R.string.save_btn_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String userNotes = input.getText().toString();

                medicine.setUserNotes(userNotes);

                ImageButton notesImgBtn = (ImageButton) addMedicineActivity.findViewById(R.id.medicine_notes_img);

                if (TextUtils.isEmpty(userNotes)) {
                    notesImgBtn.setImageResource(R.drawable.notes_empty_icon);
                } else {
                    notesImgBtn.setImageResource(R.drawable.notes_defined_icon);
                }
            }
        });

        dialog.setNegativeButton(R.string.back_btn_text, null);

        dialog.show();
    }
}
