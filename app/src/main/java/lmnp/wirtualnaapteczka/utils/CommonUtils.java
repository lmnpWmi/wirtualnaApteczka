package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;

/**
 * Class containing utils methods that can be used in a whole project.
 */

public class CommonUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static OfflineConfiguration retrieveOfflineConfiguration(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String offlineConfigurationJson = preferences.getString(OfflineConfiguration.SESSION_ITEM_NAME, OfflineConfiguration.EMPTY_JSON);

        OfflineConfiguration offlineConfiguration = new Gson().fromJson(offlineConfigurationJson, OfflineConfiguration.class);

        return offlineConfiguration;
    }

    public static void updateOfflineConfiguration(OfflineConfiguration offlineConfiguration, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(OfflineConfiguration.SESSION_ITEM_NAME, new Gson().toJson(offlineConfiguration));
        editor.apply();
    }

    public static Date parseStringToDate(String dateInTxt, Context applicationContext) throws ParseException {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(applicationContext);

        Date date = dateFormat.parse(dateInTxt);

        return date;
    }

    public static void displayNotAvaiableToastMessage(Context context) {
        Toast.makeText(context, R.string.function_not_avaiable, Toast.LENGTH_SHORT)
                .show();
    }
}
