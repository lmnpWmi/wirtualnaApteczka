package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.AddNotesOnClickListener;
import lmnp.wirtualnaapteczka.listeners.AddPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.MedicineTypeOnItemSelectedListener;
import lmnp.wirtualnaapteczka.listeners.CalendarOnClickListener;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

public class AddActivity extends AppCompatActivity {

    private Medicine newMedicine;

    private EditText medicineNameField;
    private Spinner medicineTypeSpinner;
    private TextView quantitySuffixLabel;
    private TextView dueDateCalendar;

    private LinearLayout addMedicineNotesPanel;
    private LinearLayout addMedicinePhotoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newMedicine = new Medicine();

        medicineNameField = (EditText) findViewById(R.id.medicine_name);
        medicineTypeSpinner = (Spinner) findViewById(R.id.medicine_type_spinner);
        quantitySuffixLabel = (TextView) findViewById(R.id.medicine_quantity_suffix_label);
        dueDateCalendar = (TextView) findViewById(R.id.medicine_due_date_calendar);

        addMedicineNotesPanel = (LinearLayout) findViewById(R.id.add_medicine_notes_panel);
        addMedicinePhotoPanel = (LinearLayout) findViewById(R.id.add_medicine_photo_panel);

        initializeMedicineTypeSpinner();

        dueDateCalendar.setOnClickListener(new CalendarOnClickListener(getSupportFragmentManager()));
        addMedicineNotesPanel.setOnClickListener(new AddNotesOnClickListener(this, newMedicine));
        addMedicinePhotoPanel.setOnClickListener(new AddPhotoOnClickListener(this, newMedicine));
    }

    private void initializeMedicineTypeSpinner() {
        ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element, MedicineTypeUtils.prepareListOfLocalizedTypesTOs(getApplicationContext()));
        medicineTypeSpinner.setAdapter(medicineTypesAdapter);
        medicineTypeSpinner.setOnItemSelectedListener(new MedicineTypeOnItemSelectedListener(medicineTypesAdapter, quantitySuffixLabel, getApplicationContext()));
    }
}
