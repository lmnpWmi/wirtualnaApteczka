package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.text.TextUtils;

public class LocalizationUtils {
    private LocalizationUtils() {
    }

    public static String retrieveLocalizationForString(String text, Context context) {
        String result = null;

        if (!TextUtils.isEmpty(text)) {
            String textLowerCase = text.toLowerCase();

            int typeResourceId = context.getResources()
                    .getIdentifier(textLowerCase, "string", context.getPackageName());

            result = context.getResources()
                    .getString(typeResourceId);
        }

        return result;
    }

}
