package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.customarrayadapters.MedicineItemArrayAdapter;
import lmnp.wirtualnaapteczka.entities.Medicine;
import lmnp.wirtualnaapteczka.entities.User;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;
import lmnp.wirtualnaapteczka.utils.CollectionUtils;
import lmnp.wirtualnaapteczka.utils.SessionManager;

import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private DbService dbService;
    private ListView medicineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dbService = FakeDbServiceImpl.createNewInstance();
        medicineListView = (ListView) findViewById(R.id.medicine_list_view);

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
