package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.comparators.MedicineByDueDateComparator;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemSimpleArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.FriendListOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.MedicineListOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.SessionManager;
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

        dbService = SessionManager.obtainDbService();
        user = SessionManager.getCurrentUser();

        addMedicinePanel = (LinearLayout) findViewById(R.id.add_medicine_panel);
        medicineListPanel = (LinearLayout) findViewById(R.id.medicine_list_panel);
        friendsPanel = (LinearLayout) findViewById(R.id.friends_panel);
        recentlyUsedMedicinesSimpleList = (ListView) findViewById(R.id.medicine_list_view_simple);

        initializeListeners();
        initializeRecentlyUsedMedicinesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        Collections.sort(currentUserMedicines, new MedicineByDueDateComparator());

        int entriesLimit = user.getUserPreferences().getRecentlyUsedMedicinesViewLimit();
        entriesLimit = entriesLimit > currentUserMedicines.size() ? currentUserMedicines.size() : entriesLimit;

        List<Medicine> recentlyUsedMedicines = currentUserMedicines.subList(AppConstants.FIRST_ITEM_INDEX, entriesLimit);

        return recentlyUsedMedicines;
    }
}
