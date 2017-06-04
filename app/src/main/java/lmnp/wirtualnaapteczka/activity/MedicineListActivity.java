package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.dto.MedicineItem;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * Activity class for handling medicine list.
 */
public class MedicineListActivity extends AppCompatActivity {

    private OfflineConfiguration offlineConfiguration;

    private ListView medicineList;
    private FloatingActionButton floatingBtn;

    private LinearLayout categoriesLinearLayout;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());

        initializeComponents();
        prepareCategoryButtons();

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

        categoriesLinearLayout = (LinearLayout) findViewById(R.id.categoriesLayout);
        categoriesLinearLayout.setWeightSum(offlineConfiguration.getMedicineCategories().size() + 1);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1f;
    }

    private void prepareCategoryButtons() {
        final List<String> medicineCategories = offlineConfiguration.getMedicineCategories();

        // Prepares a category button for viewing from all categories
        String categoryAllName = getApplicationContext().getString(R.string.medicinelist_categoryAll);
        Button categoryAllButton = prepareSingleCategoryButton(categoryAllName, true);

        categoriesLinearLayout.addView(categoryAllButton);

        for (final String category : medicineCategories) {
            Button categoryButton = prepareSingleCategoryButton(category, false);
            categoriesLinearLayout.addView(categoryButton);
        }
    }

    private Button prepareSingleCategoryButton(final String category, boolean isSelected) {
        Button createdButton = (Button) getLayoutInflater().inflate(R.layout.medicine_layout_top_button, null);

        createdButton.setText(category);

        if (isSelected) {
            createdButton.setTextColor(Color.rgb(201, 34, 21));
        }

        createdButton.setLayoutParams(params);
        createdButton.setOnClickListener(new CategoryButtonListener(category, categoriesLinearLayout));

        return createdButton;
    }

    private void addMedicinesFromMedicineChestToMedicinesList() {
        List<MedicineItem> medicines = offlineConfiguration.getMedicineChest().getMedicines();
        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, 0, medicines);

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

        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, 0, categoryMedicines);
        medicineList.setAdapter(medicineItemArrayAdapter);
    }

    private class CategoryButtonListener implements View.OnClickListener {

        private String category;
        private LinearLayout categoriesLinearLayout;


        CategoryButtonListener(String category, LinearLayout categoriesLinearLayout) {
            this.category = category;
            this.categoriesLinearLayout = categoriesLinearLayout;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < categoriesLinearLayout.getChildCount(); i++) {
                Button button = (Button) categoriesLinearLayout.getChildAt(i);
                button.setTextColor(Color.BLACK);
            }

            Button button = (Button) v;
            button.setTextColor(Color.rgb(201, 34, 21));

            String categoryAllName = getApplicationContext().getString(R.string.medicinelist_categoryAll);
            boolean isCategoryAllSelected = category.equals(categoryAllName);

            if (isCategoryAllSelected) {
                addMedicinesFromMedicineChestToMedicinesList();
            } else {
                addMedicinesByCategoryToMedicinesList(category);
            }
        }
    }
}