package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class DeleteMedicineOnClickListener implements View.OnClickListener {
    private String medicineId;

    public DeleteMedicineOnClickListener(String medicineId) {
        this.medicineId = medicineId;
    }

    @Override
    public void onClick(View v) {
        DbService dbService = SessionManager.getDbService();
        dbService.deleteMedicine(medicineId);

        Intent intent = new Intent(v.getContext(), MedicineListActivity.class);
        v.getContext().startActivity(intent);
    }
}
