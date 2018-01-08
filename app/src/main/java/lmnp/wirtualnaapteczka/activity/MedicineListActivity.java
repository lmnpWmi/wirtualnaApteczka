package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.data.entities.UserPreferences;
import lmnp.wirtualnaapteczka.data.entities.UserSession;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.helpers.MedicineFilter;
import lmnp.wirtualnaapteczka.helpers.SearchMedicineDialogHelper;
import lmnp.wirtualnaapteczka.listeners.mainactivity.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.FirebaseConstants;

import java.util.*;

public class MedicineListActivity extends AppCompatActivity {
    private ListView medicineListView;
    private FloatingActionButton floatingActionButton;

    private SearchMedicineDialogHelper searchMedicineDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        medicineListView = (ListView) findViewById(R.id.medicine_list_view);
        medicineListView.setEmptyView(findViewById(R.id.medicine_list_empty));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        floatingActionButton.setOnClickListener(new AddNewMedicineOnClickListener(this.getClass()));

        initializeFirebaseListeners();
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

    private void initializeFirebaseListeners() {
        FirebaseUser firebaseUser = SessionManager.getFirebaseUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userRef = database.getReference().child(FirebaseConstants.USERS).child(firebaseUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                UserPreferences userPreferences = user.getUserPreferences();
                SortingComparatorTypeEnum defaultSortingComparatorEnum = userPreferences.getDefaultSortingComparatorEnum();

                UserSession userSession = user.getUserSession();
                String searchValue = userSession.getSearchValue();

                Map<String, Medicine> medicinesMap = user.getMedicines();
                List<Medicine> medicines = new ArrayList<>(medicinesMap.values());

                if (!TextUtils.isEmpty(searchValue)) {
                    medicines = MedicineFilter.filterMedicines(searchValue, medicines, false);
                }

                initializeMedicineList(medicines, defaultSortingComparatorEnum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initializeMedicineList(List<Medicine> medicines, SortingComparatorTypeEnum defaultSortingComparatorEnum) {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }

        Comparator<Medicine> defaultComparator = defaultSortingComparatorEnum.getComparator();

        Collections.sort(medicines, defaultComparator);

        MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, R.id.medicine_list_view, medicines);
        medicineListView.setAdapter(medicineItemArrayAdapter);
    }
}
