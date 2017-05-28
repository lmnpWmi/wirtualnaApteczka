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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());

        Spinner medicineType = (Spinner) findViewById(R.id.medicineTypeEnum);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element_layout, MedicineTypeEnum.retrieveNames());
        medicineType.setAdapter(spinnerArrayAdapter);

        FloatingActionButton cancelNewMedicineBtn = (FloatingActionButton) findViewById(R.id.cancelNewMedicine);
        cancelNewMedicineBtn.setOnClickListener(this);

        FloatingActionButton saveNewMedicineBtn = (FloatingActionButton) findViewById(R.id.saveNewMedicine);
        saveNewMedicineBtn.setOnClickListener(this);
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

                    List<String> medicineCategories = offlineConfiguration.getMedicineCategories();
                    boolean doesCategoryAlreadyExists = medicineCategories.contains(medicineItem.getCategory());

                    if (!doesCategoryAlreadyExists) {
                        medicineCategories.add(medicineItem.getCategory());
                    }

                    CommonUtils.updateOfflineConfiguration(offlineConfiguration, getApplicationContext());
                } catch (ParseException e) {
                    Log.e("Parsing error", "Unable to parse dueDate. Adding new medicine failed.");
                }
                startActivity(intent);
                break;
        }
    }

    private MedicineItem prepareMedicineItem() throws ParseException {
        MedicineItem medicineItem = new MedicineItem();

        EditText medicineName = (EditText) findViewById(R.id.medicineName);
        EditText medicineCategory = (EditText) findViewById(R.id.medicineCategory);
        Spinner medicineType = (Spinner) findViewById(R.id.medicineTypeEnum);
        EditText medicineAmount = (EditText) findViewById(R.id.medicineAmount);
        EditText medicineDueDate = (EditText) findViewById(R.id.medicineDueDate);

        medicineItem.setName(medicineName.getText().toString());

        String category = medicineCategory.getText().toString();
        medicineItem.setCategory(category);

        medicineItem.setType(MedicineTypeEnum.of(medicineType.getSelectedItem().toString()));
        medicineItem.setAmount(Long.valueOf(medicineAmount.getText().toString()));

        String dateInTxt = medicineDueDate.getText().toString();
        Date dueDate = CommonUtils.parseStringToDate(dateInTxt);
        medicineItem.setDueDate(dueDate);

        return medicineItem;
    }
}
