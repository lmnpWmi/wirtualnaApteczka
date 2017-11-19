package lmnp.wirtualnaapteczka.listeners;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddActivity;

public class AddNewMedicineListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddActivity.class);
        v.getContext().startActivity(intent);
    }
}
