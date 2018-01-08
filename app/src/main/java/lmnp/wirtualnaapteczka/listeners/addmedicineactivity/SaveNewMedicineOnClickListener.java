package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

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
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.MedicineValidator;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.CollectionUtils;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;

import java.util.List;

public class SaveNewMedicineOnClickListener implements View.OnClickListener {
    private Medicine medicine;
    private boolean editingExistingMedicine;

    private AddMedicineActivity addMedicineActivity;

    private EditText medicineName;
    private EditText medicineAmount;
    private EditText medicineNotes;
    private Spinner medicineType;
    private CheckBox shareMedicineCheckbox;

    public SaveNewMedicineOnClickListener(Medicine medicine, boolean editingExistingMedicine, AddMedicineActivity addMedicineActivity) {
        this.medicine = medicine;
        this.editingExistingMedicine = editingExistingMedicine;
        this.addMedicineActivity = addMedicineActivity;
    }

    @Override
    public void onClick(View v) {
        initializeViewComponents();
        updateNewMedicineValues();

        MedicineValidationResultTO validationResult = MedicineValidator.validateMedicine(medicine, addMedicineActivity.getResources());

        if (validationResult.isMedicineValid()) {
            deleteOldThumbnailsIfExists();
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

        medicine.setName(name);
        medicine.setAmount(amount);
        medicine.setType(medicineType.getMedicineTypeEnum());
        medicine.setShareWithFriends(shareWithFriends);
        medicine.setUserNotes(notes);
    }

    private void deleteOldThumbnailsIfExists() {
        PhotoDescriptionTO photoDescriptionTO = medicine.getPhotoDescriptionTO();
        List<String> oldPhotoUrisToDelete = photoDescriptionTO.getOldPhotoUrisToDelete();

        if (CollectionUtils.isNotEmpty(oldPhotoUrisToDelete)) {
            for (String oldPhotoUriToDelete : oldPhotoUrisToDelete) {
                if (!TextUtils.isEmpty(oldPhotoUriToDelete)) {
                    PhotoUtils.deleteThumbnailFile(oldPhotoUriToDelete);
                }
            }

            photoDescriptionTO.setOldPhotoUrisToDelete(null);
        }
    }

    private void saveMedicineToDatabase() {
        DbService dbService = SessionManager.getDbService();
        dbService.saveOrUpdateMedicine(medicine);
    }

    private void displaySavedToastMessage(android.content.Context context) {
        String savedMessage;

        if (editingExistingMedicine) {
            savedMessage = context.getResources().getString(R.string.medicine_updated_msg);
        } else {
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
