package lmnp.wirtualnaapteczka.listeners.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.activity.FamilyActivity;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.fragments.MedicinesFamilyListTabFragment;

import java.util.Locale;

public class LaunchVoiceRecognitionOnClickListener implements View.OnClickListener {
    private int requestCode;

    public LaunchVoiceRecognitionOnClickListener(int requestCode) {
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
            boolean isExecutedFromAddMedicine = context instanceof AddMedicineActivity;
            boolean isExecutedFromMedicineList = context instanceof MedicineListActivity;
            boolean isExecutedFromFamilyMedicineList = context instanceof FamilyActivity;

            if (isExecutedFromAddMedicine) {
                ((AddMedicineActivity) context).startActivityForResult(intent, requestCode);
            } else if (isExecutedFromMedicineList) {
                ((MedicineListActivity) context).startActivityForResult(intent, requestCode);
            } else if (isExecutedFromFamilyMedicineList) {
                ((FamilyActivity) context).startActivityForResult(intent, requestCode);
            }
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context,
                    context.getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
