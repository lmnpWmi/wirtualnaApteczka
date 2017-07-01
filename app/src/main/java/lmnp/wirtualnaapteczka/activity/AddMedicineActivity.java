package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.components.DatePickerFragment;
import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.dto.MedicineItem;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * Activity class for handling adding new medicine form activity.
 */
public class AddMedicineActivity extends AppCompatActivity {

    private OfflineConfiguration offlineConfiguration;

    private EditText medicineAmount;
    private EditText medicineCategory;
    private TextView medicineDueDate;
    private EditText medicineName;
    private Spinner medicineType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());

        initializeComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean result = super.onOptionsItemSelected(item);

        if (id == R.id.action_add) {
            try {
                MedicineItem medicineItem = prepareMedicineItem();

                offlineConfiguration.getMedicineChest()
                        .getMedicines()
                        .add(medicineItem);

                offlineConfiguration.getMedicineCategories().add(medicineItem.getCategory());

                CommonUtils.updateOfflineConfiguration(offlineConfiguration, getApplicationContext());
            } catch (ParseException e) {
                Log.e("Parsing error", "Unable to parse dueDate. Adding new medicine failed.");
            } catch (NumberFormatException e) {
                Log.e("Parsing error", "Unable to parse amount. Adding new medicine failed.");
            }
            Intent intent = new Intent(this, MedicineListActivity.class);
            startActivity(intent);
            result = true;
        }

        return result;
    }

    private void initializeComponents() {
        medicineAmount = (EditText) findViewById(R.id.medicineAmount);
        medicineName = (EditText) findViewById(R.id.medicineName);
        medicineCategory = (EditText) findViewById(R.id.medicineCategory);
        medicineDueDate = (TextView) findViewById(R.id.medicineDueDate);

        medicineType = (Spinner) findViewById(R.id.medicineTypeEnum);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element_layout, MedicineTypeEnum.retrieveNames());
        medicineType.setAdapter(spinnerArrayAdapter);
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
        Date dueDate = CommonUtils.parseStringToDate(dateInTxt, getApplicationContext());
        medicineItem.setDueDate(dueDate);


        return medicineItem;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
