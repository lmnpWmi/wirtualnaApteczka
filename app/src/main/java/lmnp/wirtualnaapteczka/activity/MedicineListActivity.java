package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.entities.User;
import lmnp.wirtualnaapteczka.listeners.AddNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.utils.CollectionUtils;
import lmnp.wirtualnaapteczka.utils.SessionManager;

import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private DbService dbService;
    private ListView medicineListView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dbService = SessionManager.obtainDbService();
        medicineListView = (ListView) findViewById(R.id.medicine_list_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_new_medicine_btn);
        floatingActionButton.setOnClickListener(new AddNewMedicineOnClickListener());

        initializeMedicineList();
    }

    private void initializeMedicineList() {
        List<Medicine> userMedicines = retrieveCurrentUserMedicines();

        if (CollectionUtils.isNotEmpty(userMedicines)) {
            MedicineItemArrayAdapter medicineItemArrayAdapter = new MedicineItemArrayAdapter(this, R.id.medicine_list_view, userMedicines);
            medicineListView.setAdapter(medicineItemArrayAdapter);
        }
    }

    private List<Medicine> retrieveCurrentUserMedicines() {
        List<Medicine> medicineList = null;
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            medicineList = dbService.findAllMedicinesByUserId(currentUser.getId());
        }

        return medicineList;
    }
}
