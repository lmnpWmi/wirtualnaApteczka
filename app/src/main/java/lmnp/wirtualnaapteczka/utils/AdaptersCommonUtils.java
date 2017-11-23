package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import lmnp.wirtualnaapteczka.R;

public class AdaptersCommonUtils {

    private AdaptersCommonUtils() {
    }

    public static String prepareAmountText(Integer amount, Context context) {
        String amountText = context.getResources()
                .getString(R.string.amount);

        if (amount == null) {
            amount = 0;
        }

        String result = amountText + ": " + amount;

        return result;
    }


    public static Uri prepareThumbnailUri(String thumbnailPath) {
        Uri thumbnailUri = null;

        if (!TextUtils.isEmpty(thumbnailPath)) {
            thumbnailUri = new Uri.Builder()
                    .appendPath(thumbnailPath)
                    .build();
        }

        return thumbnailUri;
    }
}
