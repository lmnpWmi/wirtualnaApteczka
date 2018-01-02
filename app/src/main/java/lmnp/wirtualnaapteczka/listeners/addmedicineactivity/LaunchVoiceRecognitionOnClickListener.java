package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;

import java.util.Locale;

public class LaunchVoiceRecognitionOnClickListener implements View.OnClickListener {
    private AddMedicineActivity addMedicineActivity;
    private int requestCode;

    public LaunchVoiceRecognitionOnClickListener(AddMedicineActivity addMedicineActivity, int requestCode) {
        this.addMedicineActivity = addMedicineActivity;
        this.requestCode = requestCode;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                context.getString(R.string.speech_prompt));
        try {
            addMedicineActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context,
                    context.getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
