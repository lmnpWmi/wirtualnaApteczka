package lmnp.wirtualnaapteczka.utils;

import android.content.Context;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    private DateUtils(){
    }

    public static String formatDate(Date date, Context context) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }
}
