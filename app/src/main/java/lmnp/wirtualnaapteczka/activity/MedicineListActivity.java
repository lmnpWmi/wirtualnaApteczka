package lmnp.wirtualnaapteczka.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.comparators.MedicineByUpdatedAtComparator;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.MedicineFilter;
import lmnp.wirtualnaapteczka.session.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private DbService dbService;
    private ListView medicineListView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dbService = SessionManager.getDbService();
        medicineListView = (ListView) findViewById(R.id.medicine_list_view);
        medicineListView.setEmptyView(findViewById(R.id.medicine_list_empty));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        floatingActionButton.setOnClickListener(new AddNewMedicineOnClickListener());

        List<Medicine> medicines = dbService.findAllMedicinesForCurrentUser();
        initializeMedicineList(medicines);
    }

    private void initializeMedicineList(List<Medicine> medicines) {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }

        Collections.sort(medicines, new MedicineByUpdatedAtComparator());

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
        boolean result;

        switch (item.getItemId()) {
            case R.id.search_medicine_btn:
                openSearchDialog();
                result = true;
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

    private void openSearchDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.search_medicine_msg);

        final EditText input = new EditText(this);
        input.setSingleLine();
        dialog.setView(input);

        dialog.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String searchValue = input.getText().toString();
                List<Medicine> medicines = dbService.findAllMedicinesForCurrentUser();
                List<Medicine> filteredMedicines = MedicineFilter.filterMedicines(searchValue, medicines, false);

                initializeMedicineList(filteredMedicines);
            }
        });

        dialog.setNegativeButton(R.string.go_back, null);

        dialog.show();
    }
}
