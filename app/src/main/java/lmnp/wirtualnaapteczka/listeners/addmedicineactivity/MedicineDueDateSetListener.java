package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

import java.text.DateFormat;
import java.util.Calendar;

public class MedicineDueDateSetListener implements DatePickerDialog.OnDateSetListener {

    private Medicine medicineForUpdate;

    private Activity activity;
    private TextView medicineDueDate;


    public MedicineDueDateSetListener(Medicine medicineForUpdate, Activity activity) {
        this.medicineForUpdate = medicineForUpdate;
        this.activity = activity;
        this.medicineDueDate = (TextView) activity.findViewById(R.id.medicine_due_date_calendar);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(activity.getApplicationContext());
        String formattedDate = dateFormat.format(calendar.getTime());

        medicineDueDate.setText(formattedDate);

        medicineForUpdate.setDueDate(calendar.getTime());
    }
}
