package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class SaveNewMedicineOnClickListener implements View.OnClickListener {

    private AddMedicineActivity addMedicineActivity;
    private Medicine newMedicine;

    private EditText medicineName;
    private EditText medicineAmount;
    private Spinner medicineType;

    public SaveNewMedicineOnClickListener(AddMedicineActivity addMedicineActivity, Medicine newMedicine) {
        this.addMedicineActivity = addMedicineActivity;
        this.newMedicine = newMedicine;
    }

    @Override
    public void onClick(View v) {
        System.out.print("asd");
    }
}
