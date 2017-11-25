package lmnp.wirtualnaapteczka.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.*;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;

public class AddMedicineActivity extends AppCompatActivity {
    private Medicine medicine;

    private boolean editingExistingMedicine;

    private EditText nameEdit;
    private EditText amountEdit;
    private TextView quantitySuffixLabel;
    private TextView dueDateCalendar;
    private FloatingActionButton saveMedicineBtn;
    private Spinner medicineTypeSpinner;
    private ImageButton notesImgBtn;
    private ImageButton photoImgBtn;

    private LinearLayout addMedicineNotesPanel;
    private LinearLayout addMedicinePhotoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initializeViewComponents();

        Medicine medicineForEditing = (Medicine) getIntent().getSerializableExtra(AppConstants.EXISTING_MEDICINE);

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

    private void initializeViewComponents() {
        nameEdit = (EditText) findViewById(R.id.medicine_name);
        nameEdit.setSelected(false);
        amountEdit = (EditText) findViewById(R.id.medicine_amount);
        amountEdit.setSelected(false);
        quantitySuffixLabel = (TextView) findViewById(R.id.medicine_quantity_suffix_label);
        dueDateCalendar = (TextView) findViewById(R.id.medicine_due_date_calendar);
        saveMedicineBtn = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        medicineTypeSpinner = (Spinner) findViewById(R.id.medicine_type_spinner);
        notesImgBtn = (ImageButton) findViewById(R.id.medicine_notes_img);
        photoImgBtn = (ImageButton) findViewById(R.id.medicine_photo_img);

        addMedicineNotesPanel = (LinearLayout) findViewById(R.id.add_medicine_notes_panel);
        addMedicinePhotoPanel = (LinearLayout) findViewById(R.id.add_medicine_photo_panel);
    }

    private void updateComponentsValues() {
        nameEdit.setText(medicine.getName());

        if (medicine.getDueDate() != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String formattedDate = dateFormat.format(medicine.getDueDate());

            dueDateCalendar.setText(formattedDate);
        }

        if (medicine.getAmount() != null) {
            amountEdit.setText(String.valueOf(medicine.getAmount()));
        }

        if (!TextUtils.isEmpty(medicine.getUserNotes())) {
            notesImgBtn.setImageResource(R.drawable.notes_defined_icon);
        }

        if (!TextUtils.isEmpty(medicine.getThumbnailUri())) {
            Uri uri = Uri.parse(medicine.getThumbnailUri());
            Log.w("URI!!", uri.toString());
            Bitmap photoBitmap = null;
            try {
                photoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoImgBtn.setImageBitmap(ThumbnailUtils.extractThumbnail(photoBitmap, photoBitmap.getWidth(), photoBitmap.getHeight()));
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
        addMedicineNotesPanel.setOnClickListener(new AddNotesOnClickListener(this, medicine));
        addMedicinePhotoPanel.setOnClickListener(new AddPhotoOnClickListener(this, medicine));
        saveMedicineBtn.setOnClickListener(new SaveNewMedicineOnClickListener(medicine, editingExistingMedicine, this));
    }
}
