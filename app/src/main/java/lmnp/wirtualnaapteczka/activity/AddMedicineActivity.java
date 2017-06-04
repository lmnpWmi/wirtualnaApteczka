package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.dto.MedicineItem;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * Activity class for handling adding new medicine form activity.
 */
public class AddMedicineActivity extends AppCompatActivity implements View.OnClickListener {

    private OfflineConfiguration offlineConfiguration;

    private EditText medicineAmount;
    private EditText medicineCategory;
    private EditText medicineDueDate;
    private EditText medicineName;
    private Spinner medicineType;

    private FloatingActionButton saveNewMedicineBtn;
    private FloatingActionButton cancelNewMedicineBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());

        initializeComponents();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MedicineListActivity.class);

        switch (v.getId()) {
            case R.id.cancelNewMedicine:
                startActivity(intent);
                break;
            case R.id.saveNewMedicine:
                try {
                    MedicineItem medicineItem = prepareMedicineItem();

                    offlineConfiguration.getMedicineChest()
                            .getMedicines()
                            .add(medicineItem);

                    addCategoryToOfflineConfigurationIfDoesntExists(medicineItem);

                    CommonUtils.updateOfflineConfiguration(offlineConfiguration, getApplicationContext());
                } catch (ParseException e) {
                    Log.e("Parsing error", "Unable to parse dueDate. Adding new medicine failed.");
                }
                startActivity(intent);
                break;
        }
    }

    private void initializeComponents() {
        medicineAmount = (EditText) findViewById(R.id.medicineAmount);
        medicineName = (EditText) findViewById(R.id.medicineName);
        medicineCategory = (EditText) findViewById(R.id.medicineCategory);
        medicineDueDate = (EditText) findViewById(R.id.medicineDueDate);

        medicineType = (Spinner) findViewById(R.id.medicineTypeEnum);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element_layout, MedicineTypeEnum.retrieveNames());
        medicineType.setAdapter(spinnerArrayAdapter);

        cancelNewMedicineBtn = (FloatingActionButton) findViewById(R.id.cancelNewMedicine);
        cancelNewMedicineBtn.setOnClickListener(this);

        saveNewMedicineBtn = (FloatingActionButton) findViewById(R.id.saveNewMedicine);
        saveNewMedicineBtn.setOnClickListener(this);
    }

    private MedicineItem prepareMedicineItem() throws ParseException {
        MedicineItem medicineItem = new MedicineItem();

        String name = medicineName.getText().toString();
        medicineItem.setName(name);

        String category = medicineCategory.getText().toString();
        medicineItem.setCategory(category);

        MedicineTypeEnum typeEnum = MedicineTypeEnum.of(medicineType.getSelectedItem().toString());
        medicineItem.setType(typeEnum);

        Long amount = Long.valueOf(medicineAmount.getText().toString());
        medicineItem.setAmount(amount);

        String dateInTxt = medicineDueDate.getText().toString();
        Date dueDate = CommonUtils.parseStringToDate(dateInTxt);
        medicineItem.setDueDate(dueDate);

        return medicineItem;
    }

    private void addCategoryToOfflineConfigurationIfDoesntExists(MedicineItem medicineItem) {
        List<String> medicineCategories = offlineConfiguration.getMedicineCategories();
        boolean doesCategoryAlreadyExists = medicineCategories.contains(medicineItem.getCategory());

        if (!doesCategoryAlreadyExists) {
            medicineCategories.add(medicineItem.getCategory());
        }
    }
}
