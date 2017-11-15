package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.entities.Medicine;
import lmnp.wirtualnaapteczka.entities.User;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FakeDbServiceImpl;
import lmnp.wirtualnaapteczka.utils.SessionManager;

import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private DbService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dbService = FakeDbServiceImpl.createNewInstance();

        retrieveCurrentUserMedicines();
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
