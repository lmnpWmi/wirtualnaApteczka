package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;

public class MedicineListOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MedicineListActivity.class);
        v.getContext().startActivity(intent);
    }
}
