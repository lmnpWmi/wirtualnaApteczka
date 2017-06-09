package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.dto.MedicineItem;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * Activity class for handling medicine list.
 */
public class MedicineListActivity extends AppCompatActivity {

    private OfflineConfiguration offlineConfiguration;

    private Spinner categorySpinner;
    private ListView medicineList;
    private FloatingActionButton floatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());

        initializeComponents();
        prepareCategorySpinner();

        boolean isMedicineChestNotEmpty = !offlineConfiguration.getMedicineChest().getMedicines().isEmpty();

        if (isMedicineChestNotEmpty) {
            addMedicinesFromMedicineChestToMedicinesList();
        }
    }

    private void initializeComponents() {
        medicineList = (ListView) findViewById(R.id.medicineListView);
        TextView emptyTextView = (TextView) findViewById(R.id.empty);
        medicineList.setEmptyView(emptyTextView);

        floatingBtn = (FloatingActionButton) findViewById(R.id.addMedicineFAB);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(new CategorySpinnerItemSelectedListener());
    }

    private void prepareCategorySpinner() {
        String allCategoryText = getResources().getString(R.string.medicinelist_categoryAll);

        List<String> medicineCategories = new ArrayList<>();
        medicineCategories.add(allCategoryText);
        medicineCategories.addAll(offlineConfiguration.getMedicineCategories());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_category_layout, medicineCategories);
        categorySpinner.setAdapter(spinnerArrayAdapter);
    }

    private void addMedicinesFromMedicineChestToMedicinesList() {
        List<MedicineItem> medicines = offlineConfiguration.getMedicineChest().getMedicines();
        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, 0, medicines, categorySpinner);

        medicineList.setAdapter(medicineItemArrayAdapter);
    }

    private void addMedicinesByCategoryToMedicinesList(String category) {
        List<MedicineItem> allMedicines = offlineConfiguration.getMedicineChest().getMedicines();
        List<MedicineItem> categoryMedicines = new ArrayList<>();

        for (MedicineItem medicine : allMedicines) {
            if (medicine.getCategory().equals(category)) {
                categoryMedicines.add(medicine);
            }
        }

        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, 0, categoryMedicines, categorySpinner);
        medicineList.setAdapter(medicineItemArrayAdapter);
    }

    private class CategorySpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedCategory = categorySpinner.getItemAtPosition(position).toString();

            boolean isAllCategorySelected = selectedCategory.equals(getResources().getString(R.string.medicinelist_categoryAll));

            if (isAllCategorySelected) {
                addMedicinesFromMedicineChestToMedicinesList();
            } else {
                addMedicinesByCategoryToMedicinesList(selectedCategory);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}