package lmnp.wirtualnaapteczka.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.comparators.MedicineModifiedComparator;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemSimpleArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.FriendListOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.MedicineListOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbService dbService;
    private User user;

    private LinearLayout addMedicinePanel;
    private LinearLayout medicineListPanel;
    private LinearLayout friendsPanel;
    private ListView recentlyUsedMedicinesSimpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbService = SessionManager.getDbService();
        user = SessionManager.getCurrentUser();

        addMedicinePanel = (LinearLayout) findViewById(R.id.add_medicine_panel);
        medicineListPanel = (LinearLayout) findViewById(R.id.medicine_list_panel);
        friendsPanel = (LinearLayout) findViewById(R.id.friends_panel);
        recentlyUsedMedicinesSimpleList = (ListView) findViewById(R.id.medicine_list_view_simple);
        recentlyUsedMedicinesSimpleList.setEmptyView(findViewById(R.id.medicine_list_simple_empty));

        initializeListeners();
        initializeRecentlyUsedMedicinesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public void finish() {
        displayLogoutPopup();
    }

    public void initializeRecentlyUsedMedicinesList() {
        List<Medicine> recentlyUsedMedicinesList = prepareRecentlyUsedMedicinesList();
        MedicineItemSimpleArrayAdapter recentlyUsedMedicinesAdapter = new MedicineItemSimpleArrayAdapter(this, R.id.medicine_list_view_simple, recentlyUsedMedicinesList);

        recentlyUsedMedicinesSimpleList.setAdapter(recentlyUsedMedicinesAdapter);
    }

    private void initializeListeners() {
        addMedicinePanel.setOnClickListener(new AddNewMedicineOnClickListener());
        medicineListPanel.setOnClickListener(new MedicineListOnClickListener());
        friendsPanel.setOnClickListener(new FriendListOnClickListener());
    }

    private List<Medicine> prepareRecentlyUsedMedicinesList() {
        List<Medicine> currentUserMedicines = dbService.findAllMedicinesByUserId(SessionManager.getCurrentUser().getId());
        Collections.sort(currentUserMedicines, new MedicineModifiedComparator(false));

        int entriesLimit = user.getUserPreferences().getRecentlyUsedMedicinesViewLimit();
        entriesLimit = entriesLimit > currentUserMedicines.size() ? currentUserMedicines.size() : entriesLimit;

        List<Medicine> recentlyUsedMedicines = currentUserMedicines.subList(AppConstants.FIRST_ITEM_INDEX, entriesLimit);

        return recentlyUsedMedicines;
    }

    private void displayLogoutPopup() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.log_out_popup_msg);

        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // logout
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        dialog.setNegativeButton(R.string.no, null);
        dialog.show();
    }
}
