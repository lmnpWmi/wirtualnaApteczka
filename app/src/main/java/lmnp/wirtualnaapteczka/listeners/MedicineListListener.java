package lmnp.wirtualnaapteczka.listeners;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;

public class MedicineListListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MedicineListActivity.class);
        v.getContext().startActivity(intent);
    }
}
