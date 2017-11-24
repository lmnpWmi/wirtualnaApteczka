package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;

public class AddNewMedicineOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
        v.getContext().startActivity(intent);
    }
}
