package lmnp.wirtualnaapteczka.utils;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.widget.EditText;
import lmnp.wirtualnaapteczka.helpers.SearchMedicineDialogHelper;

import java.util.List;

public final class VoiceRecognitionUtils {
    private VoiceRecognitionUtils() {
    }

    public static void updateRetrievedNameInSearchDialog(SearchMedicineDialogHelper searchMedicineDialogHelper, Intent data) {
        List<String> voiceRecognizedData = data
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        String potentialMedicineName = voiceRecognizedData.get(0);

        EditText searchMedicineEditText = searchMedicineDialogHelper.getSearchMedicineEditText();
        searchMedicineEditText.setText(potentialMedicineName);
    }

    public static String capitalizeFirstLetterInRetrievedText(String voicedText) {
        String firstLetterCapitalizedVoicedText = null;
        if (!TextUtils.isEmpty(voicedText)) {
            if (voicedText.length() == 1) {
                firstLetterCapitalizedVoicedText = voicedText.toUpperCase();
            } else {
                firstLetterCapitalizedVoicedText = voicedText.substring(0, 1).toUpperCase() + voicedText.substring(1);
            }
        }

        return firstLetterCapitalizedVoicedText;
    }
}
