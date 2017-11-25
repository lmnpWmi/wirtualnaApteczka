package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.io.File;
import java.io.IOException;

public class ImageCaptureActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE = 1;
    private static final String DATA = "data";

    private Uri createdPhotoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        if (savedInstanceState != null)
        {
            createdPhotoURI = savedInstanceState.getParcelable("outputFileUri");
        }

        launchCamera();
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        createdPhotoURI = Uri.fromFile(preparePhotoFile());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, createdPhotoURI);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("createdPhotoURI", createdPhotoURI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, datalg);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Medicine medicine = (Medicine) getIntent().getSerializableExtra(AppConstants.EXISTING_MEDICINE);
                    medicine.setThumbnailUri(createdPhotoURI.toString());

                    Log.w("UWAGA createdUR", createdPhotoURI.toString());

                    Intent intent = new Intent(getApplicationContext(), AddMedicineActivity.class);
                    intent.putExtra(AppConstants.EXISTING_MEDICINE, medicine);

                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File preparePhotoFile() {
        File medicinesPhotosDir = new File(getApplicationContext().getFilesDir(), AppConstants.MEDICINE_PHOTOS);

        if (!medicinesPhotosDir.exists()) {
            medicinesPhotosDir.mkdir();
        }

        String fileName = "plik";
        File photoFile = null;
        try {
            photoFile = File.createTempFile(fileName, ".jpg", medicinesPhotosDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoFile;
    }
}
