package lmnp.wirtualnaapteczka.listeners;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import lmnp.wirtualnaapteczka.components.DatePickerFragment;

public class CalendarOnClickListener implements View.OnClickListener {

    private FragmentManager fragmentManager;

    public CalendarOnClickListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(fragmentManager, "datePicker");
    }
}
