package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.*;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

public class AddMedicineActivity extends AppCompatActivity {

    private Medicine newMedicine;

    private TextView quantitySuffixLabel;
    private TextView dueDateCalendar;
    private FloatingActionButton saveMedicineBtn;
    private Spinner medicineTypeSpinner;

    private LinearLayout addMedicineNotesPanel;
    private LinearLayout addMedicinePhotoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newMedicine = new Medicine();

        quantitySuffixLabel = (TextView) findViewById(R.id.medicine_quantity_suffix_label);
        dueDateCalendar = (TextView) findViewById(R.id.medicine_due_date_calendar);
        saveMedicineBtn = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        medicineTypeSpinner = (Spinner) findViewById(R.id.medicine_type_spinner);

        addMedicineNotesPanel = (LinearLayout) findViewById(R.id.add_medicine_notes_panel);
        addMedicinePhotoPanel = (LinearLayout) findViewById(R.id.add_medicine_photo_panel);

        initializeMedicineTypeSpinner();

        dueDateCalendar.setOnClickListener(new CalendarOnClickListener(getSupportFragmentManager(), newMedicine));
        addMedicineNotesPanel.setOnClickListener(new AddNotesOnClickListener(this, newMedicine));
        addMedicinePhotoPanel.setOnClickListener(new AddPhotoOnClickListener(this, newMedicine));
        saveMedicineBtn.setOnClickListener(new SaveNewMedicineOnClickListener(this, newMedicine));
    }

    private void initializeMedicineTypeSpinner() {
        ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element, MedicineTypeUtils.prepareListOfLocalizedTypesTOs(getApplicationContext()));
        medicineTypeSpinner.setAdapter(medicineTypesAdapter);
        medicineTypeSpinner.setOnItemSelectedListener(new MedicineTypeOnItemSelectedListener(newMedicine, medicineTypesAdapter, quantitySuffixLabel, getApplicationContext()));
    }
}
