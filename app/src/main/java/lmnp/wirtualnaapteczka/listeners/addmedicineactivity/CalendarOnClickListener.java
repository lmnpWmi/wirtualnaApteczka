package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.support.v4.app.FragmentManager;
import android.view.View;
import lmnp.wirtualnaapteczka.components.DatePickerFragment;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class CalendarOnClickListener implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private Medicine newMedicine;

    public CalendarOnClickListener(FragmentManager fragmentManager, Medicine newMedicine) {
        this.fragmentManager = fragmentManager;
        this.newMedicine = newMedicine;
    }

    @Override
    public void onClick(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setMedicineForUpdate(newMedicine);
        newFragment.show(fragmentManager, "datePicker");
    }
}
