package lmnp.wirtualnaapteczka.listeners.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.PreviewPhotoActivity;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class PreviewPhotoOnClickListener implements View.OnClickListener {
    private String photoUri;
    private Class<? extends AppCompatActivity> invokingClass;

    public PreviewPhotoOnClickListener(String photoUri, Class<? extends AppCompatActivity> invokingClass) {
        this.photoUri = photoUri;
        this.invokingClass = invokingClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PreviewPhotoActivity.class);
        intent.putExtra(AppConstants.MEDICINE_PHOTO_URI, photoUri);
        intent.putExtra(AppConstants.INVOKING_CLASS, invokingClass);

        v.getContext().startActivity(intent);
    }
}
