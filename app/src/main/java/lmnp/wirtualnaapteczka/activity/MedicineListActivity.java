package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.helpers.SearchMedicineDialogHelper;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private DbService dbService;
    private ListView medicineListView;
    private FloatingActionButton floatingActionButton;

    private SearchMedicineDialogHelper searchMedicineDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dbService = SessionManager.getDbService();
        medicineListView = (ListView) findViewById(R.id.medicine_list_view);
        medicineListView.setEmptyView(findViewById(R.id.medicine_list_empty));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        floatingActionButton.setOnClickListener(new AddNewMedicineOnClickListener(this.getClass()));

        List<Medicine> medicines = dbService.findAllMedicinesForCurrentUser();
        initializeMedicineList(medicines);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME && searchMedicineDialogHelper != null) {
                List<String> voiceRecognizedData = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String potentialMedicineName = voiceRecognizedData.get(0);

                EditText searchMedicineEditText = searchMedicineDialogHelper.getSearchMedicineEditText();
                searchMedicineEditText.setText(potentialMedicineName);
            }
        }
    }

    public void initializeMedicineList(List<Medicine> medicines) {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }

        Comparator<Medicine> defaultComparator = obtainDefaultMedicineListComparator();

        Collections.sort(medicines, defaultComparator);

        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, R.id.medicine_list_view, medicines);
        medicineListView.setAdapter(medicineItemArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.search_medicine_btn:
                searchMedicineDialogHelper = new SearchMedicineDialogHelper();
                searchMedicineDialogHelper.initializeDialog(this);
                break;
            case R.id.sort_btn:
                AlertDialogPreparator.showSortListDialog(this);
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    public void finish() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    private Comparator<Medicine> obtainDefaultMedicineListComparator() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);

        String defaultComparatorName = sharedPreferences.getString(AppConstants.DEFAULT_COMPARATOR, SortingComparatorTypeEnum.MODIFIED_DESCENDING.name());
        SortingComparatorTypeEnum defaultComparatorEnum = SortingComparatorTypeEnum.valueOf(defaultComparatorName);

        Comparator<Medicine> defaultComparator = defaultComparatorEnum.getComparator();
        return defaultComparator;
    }
}
