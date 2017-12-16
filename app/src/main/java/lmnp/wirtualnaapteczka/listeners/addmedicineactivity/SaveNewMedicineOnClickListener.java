package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.dto.MedicineValidationResultTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.MedicineValidator;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class SaveNewMedicineOnClickListener implements View.OnClickListener {
    private Medicine newMedicine;
    private boolean editingExistingMedicine;

    private AddMedicineActivity addMedicineActivity;

    private EditText medicineName;
    private EditText medicineAmount;
    private EditText medicineNotes;
    private Spinner medicineType;
    private CheckBox shareMedicineCheckbox;

    public SaveNewMedicineOnClickListener(Medicine newMedicine, boolean editingExistingMedicine, AddMedicineActivity addMedicineActivity) {
        this.newMedicine = newMedicine;
        this.editingExistingMedicine = editingExistingMedicine;
        this.addMedicineActivity = addMedicineActivity;
    }

    @Override
    public void onClick(View v) {
        initializeViewComponents();
        updateNewMedicineValues();

        MedicineValidationResultTO validationResult = MedicineValidator.validateMedicine(newMedicine, addMedicineActivity.getResources());

        if (validationResult.isMedicineValid()) {
            saveMedicineToDatabase();

            Class<?> targetActivity = editingExistingMedicine ? MedicineListActivity.class : MainActivity.class;

            Intent intent = new Intent(v.getContext(), targetActivity);
            v.getContext().startActivity(intent);

            displaySavedToastMessage(v.getContext());
        } else {
            displayErrorOutput(validationResult);
        }
    }

    private void initializeViewComponents() {
        medicineName = (EditText) addMedicineActivity.findViewById(R.id.medicine_name);
        medicineAmount = (EditText) addMedicineActivity.findViewById(R.id.medicine_amount);
        medicineNotes = (EditText) addMedicineActivity.findViewById(R.id.notes_text);
        medicineType = (Spinner) addMedicineActivity.findViewById(R.id.medicine_type_spinner);
        shareMedicineCheckbox = (CheckBox) addMedicineActivity.findViewById(R.id.share_medicine_checkbox);
    }

    private void updateNewMedicineValues() {
        String name = medicineName.getText().toString();
        String notes = medicineNotes.getText().toString();
        Integer amount = prepareAmountValue();
        MedicineTypeWithLocalizationTO medicineType = (MedicineTypeWithLocalizationTO) this.medicineType.getSelectedItem();
        boolean shareWithFriends = shareMedicineCheckbox.isChecked();

        newMedicine.setName(name);
        newMedicine.setAmount(amount);
        newMedicine.setType(medicineType.getMedicineTypeEnum());
        newMedicine.setShareWithFriends(shareWithFriends);
        newMedicine.setUserNotes(notes);
    }

    private void saveMedicineToDatabase() {
        DbService dbService = SessionManager.getDbService();
        dbService.saveOrUpdateMedicine(newMedicine);
    }

    private void displaySavedToastMessage(Context context) {
        String savedMessage;

        if (editingExistingMedicine) {
            savedMessage = context.getResources().getString(R.string.medicine_updated_msg);
        }
        else {
            savedMessage = context.getResources().getString(R.string.medicine_added_msg);
        }

        Toast.makeText(context, savedMessage, Toast.LENGTH_SHORT).show();
    }

    private void displayErrorOutput(MedicineValidationResultTO validationResult) {
        String errorOutput = validationResult.prepareErrorMessageOutput();
        Toast.makeText(addMedicineActivity.getApplicationContext(), errorOutput, Toast.LENGTH_LONG)
                .show();
    }

    private Integer prepareAmountValue() {
        String amountText = medicineAmount.getText().toString();
        Integer amountInt = TextUtils.isEmpty(amountText) ? null : Integer.valueOf(amountText);

        return amountInt;
    }
}
