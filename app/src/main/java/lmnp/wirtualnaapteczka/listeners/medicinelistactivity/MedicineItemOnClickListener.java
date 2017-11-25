package lmnp.wirtualnaapteczka.listeners.medicinelistactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class MedicineItemOnClickListener implements View.OnClickListener {

    private Medicine medicine;

    public MedicineItemOnClickListener(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
        intent.putExtra(AppConstants.EXISTING_MEDICINE, medicine);

        v.getContext().startActivity(intent);
    }
}
