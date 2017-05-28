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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        medicineList = (ListView) findViewById(R.id.medicineListView);
        TextView emptyTextView = (TextView) findViewById(R.id.empty);
        medicineList.setEmptyView(emptyTextView);

        offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(getApplicationContext());
        prepareCategoryButtons();

        boolean isThereAnyMedicineInMedicineChest = !offlineConfiguration.getMedicineChest().getMedicines().isEmpty();

        if (isThereAnyMedicineInMedicineChest) {
            addAllMedicinesToMedicinesList();
        }

        FloatingActionButton button = (FloatingActionButton ) findViewById(R.id.addMedicineFAB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareCategoryButtons() {
        final List<String> medicineCategories = offlineConfiguration.getMedicineCategories();

        final LinearLayout categoriesLinearLayout = (LinearLayout) findViewById(R.id.categoriesLayout);
        categoriesLinearLayout.setWeightSum(medicineCategories.size() + 1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1f;

        // Prepares a category button for viewing from all categories
        Button categoryAllButton = (Button) getLayoutInflater().inflate(R.layout.medicine_layout_top_button, null);
        categoryAllButton.setText(R.string.medicinelist_categoryAll);
        categoryAllButton.setTextColor(Color.rgb(201, 34, 21));
        categoryAllButton.setLayoutParams(params);
        categoryAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < categoriesLinearLayout.getChildCount(); i++) {
                    Button button = (Button) categoriesLinearLayout.getChildAt(i);
                    button.setTextColor(Color.BLACK);
                }

                Button button = (Button) v;
                button.setTextColor(Color.rgb(201, 34, 21));
                addAllMedicinesToMedicinesList();
            }
        });

        categoriesLinearLayout.addView(categoryAllButton);

        for (final String category : medicineCategories) {
            Button categoryButton = (Button) getLayoutInflater().inflate(R.layout.medicine_layout_top_button, null);
            categoryButton.setText(category);
            categoryButton.setLayoutParams(params);
            categoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < categoriesLinearLayout.getChildCount(); i++) {
                        Button button = (Button) categoriesLinearLayout.getChildAt(i);
                        button.setTextColor(Color.BLACK);
                    }

                    Button button = (Button) v;
                    button.setTextColor(Color.rgb(201, 34, 21));
                    addMedicinesByCategoryToMedicinesList(category);
                }
            });

            categoriesLinearLayout.addView(categoryButton);
        }
    }

    private void addAllMedicinesToMedicinesList() {
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
}
