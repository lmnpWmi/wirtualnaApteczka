package lmnp.wirtualnaapteczka.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.MedicineDueDateSetListener;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private Medicine medicineForUpdate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        if (medicineForUpdate.getDueDate() != null) {
            calendar.setTime(medicineForUpdate.getDueDate());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), new MedicineDueDateSetListener(medicineForUpdate, getActivity()), year, month, day);
    }

    public void setMedicineForUpdate(Medicine medicineForUpdate) {
        this.medicineForUpdate = medicineForUpdate;
    }
}