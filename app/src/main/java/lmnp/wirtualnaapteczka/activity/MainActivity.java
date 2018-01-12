package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.MedicineItemSimpleArrayAdapter;
import lmnp.wirtualnaapteczka.comparators.MedicineModifiedComparator;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.FamilyListOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.MedicineListOnClickListener;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LinearLayout addMedicinePanel;
    private LinearLayout medicineListPanel;
    private LinearLayout familyPanel;
    private ListView recentlyUsedMedicinesSimpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager.clearSearchValueInUserSession();

        addMedicinePanel = (LinearLayout) findViewById(R.id.add_medicine_panel);
        medicineListPanel = (LinearLayout) findViewById(R.id.medicine_list_panel);
        familyPanel = (LinearLayout) findViewById(R.id.family_panel);
        recentlyUsedMedicinesSimpleList = (ListView) findViewById(R.id.medicine_list_view_simple);
        recentlyUsedMedicinesSimpleList.setEmptyView(findViewById(R.id.medicine_list_simple_empty));

        initializeListeners();
        initializeFirebaseListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.logout_menu:
                AlertDialogPreparator.displayLogoutPopup(this);
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        SessionManager.closeApplication(this);
    }

    public void initializeRecentlyUsedMedicinesList(List<Medicine> currentUserMedicines) {
        List<Medicine> recentlyUsedMedicinesList = prepareRecentlyUsedMedicinesList(currentUserMedicines);
        MedicineItemSimpleArrayAdapter recentlyUsedMedicinesAdapter = new MedicineItemSimpleArrayAdapter(this, R.id.medicine_list_view_simple, recentlyUsedMedicinesList);

        recentlyUsedMedicinesSimpleList.setAdapter(recentlyUsedMedicinesAdapter);
    }

    private void initializeListeners() {
        addMedicinePanel.setOnClickListener(new AddNewMedicineOnClickListener(this.getClass()));
        medicineListPanel.setOnClickListener(new MedicineListOnClickListener());
        familyPanel.setOnClickListener(new FamilyListOnClickListener());
    }

    private void initializeFirebaseListeners() {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userRef = database.getReference().child(FirebaseConstants.USER_MEDICINES).child(firebaseUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Medicine>> medicineMapTypeIndicator = new GenericTypeIndicator<Map<String, Medicine>>() {};
                Map<String, Medicine> medicineIdToMedicineMap = dataSnapshot.getValue(medicineMapTypeIndicator);

                List<Medicine> currentUserMedicines;

                if (medicineIdToMedicineMap == null) {
                    currentUserMedicines = new ArrayList<>();
                }
                else{
                    currentUserMedicines = new ArrayList<>(medicineIdToMedicineMap.values());
                }

                initializeRecentlyUsedMedicinesList(currentUserMedicines);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private List<Medicine> prepareRecentlyUsedMedicinesList(List<Medicine> currentUserMedicines) {
        Collections.sort(currentUserMedicines, new MedicineModifiedComparator(false));

        int entriesLimit = AppConstants.RECENTLY_USED_MEDICINES_DEFAULT_AMOUNT;
        entriesLimit = entriesLimit > currentUserMedicines.size() ? currentUserMedicines.size() : entriesLimit;

        List<Medicine> recentlyUsedMedicines = currentUserMedicines.subList(AppConstants.FIRST_ITEM_INDEX, entriesLimit);

        return recentlyUsedMedicines;
    }
}
