package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.*;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.io.File;
import java.text.DateFormat;

public class AddMedicineActivity extends AppCompatActivity {
    private Medicine medicine;

    private boolean editingExistingMedicine;

    private EditText nameEdit;
    private EditText amountEdit;
    private EditText notesEdit;
    private TextView quantitySuffixLabel;
    private TextView dueDateCalendar;
    private FloatingActionButton saveMedicineBtn;
    private FloatingActionButton deleteMedicineBtn;
    private Spinner medicineTypeSpinner;
    private ImageButton addMedicinePhotoImg;
    private CheckBox shareMedicineWithFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initializeViewComponents();

        Medicine medicineForEditing = (Medicine) getIntent().getSerializableExtra(AppConstants.MEDICINE);

        if (medicineForEditing != null) {
            medicine = medicineForEditing;
            editingExistingMedicine = true;
            updateComponentsValues();
        } else {
            medicine = new Medicine();
        }


        initializeMedicineTypeSpinner();
        initializeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    setMedicineThumbnail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initializeViewComponents() {
        nameEdit = (EditText) findViewById(R.id.medicine_name);
        nameEdit.setSelected(false);
        amountEdit = (EditText) findViewById(R.id.medicine_amount);
        amountEdit.setSelected(false);
        notesEdit = (EditText) findViewById(R.id.notes_text);
        notesEdit.setSelected(false);

        quantitySuffixLabel = (TextView) findViewById(R.id.medicine_quantity_suffix_label);
        dueDateCalendar = (TextView) findViewById(R.id.medicine_due_date_calendar);
        saveMedicineBtn = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        medicineTypeSpinner = (Spinner) findViewById(R.id.medicine_type_spinner);
        addMedicinePhotoImg = (ImageButton) findViewById(R.id.add_medicine_photo);
        shareMedicineWithFriends = (CheckBox) findViewById(R.id.share_medicine_checkbox);

        deleteMedicineBtn = (FloatingActionButton) findViewById(R.id.delete_medicine_btn);
    }

    private void updateComponentsValues() {
        nameEdit.setText(medicine.getName());
        deleteMedicineBtn.setVisibility(View.VISIBLE);
        notesEdit.setText(medicine.getUserNotes());
        shareMedicineWithFriends.setChecked(medicine.isShareWithFriends());

        if (medicine.getDueDate() != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String formattedDate = dateFormat.format(medicine.getDueDate());

            dueDateCalendar.setText(formattedDate);
        }

        if (medicine.getAmount() != null) {
            amountEdit.setText(String.valueOf(medicine.getAmount()));
        }

        if (!TextUtils.isEmpty(medicine.getThumbnailUri())) {
            setMedicineThumbnail();
        }
    }

    private void initializeMedicineTypeSpinner() {
        ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element, MedicineTypeUtils.prepareListOfLocalizedTypesTOs(getApplicationContext()));
        medicineTypeSpinner.setAdapter(medicineTypesAdapter);

        if (medicine.getType() != null) {
            MedicineTypeWithLocalizationTO medicineTypeWithLocalizationTO = MedicineTypeUtils.prepareLocalizedTypeTO(medicine.getType(), this);
            int position = medicineTypesAdapter.getPosition(medicineTypeWithLocalizationTO);
            medicineTypeSpinner.setSelection(position);
        }

        medicineTypeSpinner.setOnItemSelectedListener(new MedicineTypeOnItemSelectedListener(medicine, medicineTypesAdapter, quantitySuffixLabel, getApplicationContext()));
    }

    private void initializeListeners() {
        dueDateCalendar.setOnClickListener(new CalendarOnClickListener(getSupportFragmentManager(), medicine));
        addMedicinePhotoImg.setOnClickListener(new AddPhotoOnClickListener(this, medicine));
        saveMedicineBtn.setOnClickListener(new SaveNewMedicineOnClickListener(medicine, editingExistingMedicine, this));

        if (editingExistingMedicine) {
            deleteMedicineBtn.setOnClickListener(new DeleteMedicineOnClickListener(medicine.getId()));
        }
    }

    private void setMedicineThumbnail() {
        Uri uri = Uri.parse(medicine.getThumbnailUri());
        File file = new File(uri.toString());
        Log.w("URI!!", file.exists() ? "Plik istnieje" : "Plik nie istnieje");
        Log.w("URI!!", uri.toString());

        File medicineDir = new File(getFilesDir(), AppConstants.MEDICINE_PHOTOS);
        File testFile = new File(medicineDir, "plik.png");

        Bitmap bitmap = BitmapFactory.decodeFile(testFile.getAbsolutePath());

        Log.w("TEST FILE URI!!", testFile.exists() ? "Plik istnieje" : "Plik nie istnieje");

        addMedicinePhotoImg.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight()));
    }
}
