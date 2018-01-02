package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class AddNewMedicineOnClickListener implements View.OnClickListener {
    private Class<? extends AppCompatActivity> invokingClass;

    public AddNewMedicineOnClickListener(Class<? extends AppCompatActivity> invokingClass) {
        this.invokingClass = invokingClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddMedicineActivity.class);
        intent.putExtra(AppConstants.INVOKING_CLASS, invokingClass);
        v.getContext().startActivity(intent);
    }
}
