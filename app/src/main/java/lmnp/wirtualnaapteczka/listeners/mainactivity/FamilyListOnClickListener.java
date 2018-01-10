package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.FamilyActivity;

public class FamilyListOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), FamilyActivity.class);
        v.getContext().startActivity(intent);
    }
}
