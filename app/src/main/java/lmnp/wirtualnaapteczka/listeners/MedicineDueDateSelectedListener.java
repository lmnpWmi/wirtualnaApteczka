package lmnp.wirtualnaapteczka.listeners;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;

import java.text.DateFormat;
import java.util.Calendar;

public class MedicineDueDateSelectedListener implements DatePickerDialog.OnDateSetListener {

    private Activity activity;
    private TextView medicineDueDate;

    public MedicineDueDateSelectedListener(Activity activity) {
        this.activity = activity;
        this.medicineDueDate = (TextView) activity.findViewById(R.id.medicine_due_date_calendar);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(activity.getApplicationContext());
        String formattedDate = dateFormat.format(calendar.getTime());

        medicineDueDate.setText(formattedDate);
    }
}
