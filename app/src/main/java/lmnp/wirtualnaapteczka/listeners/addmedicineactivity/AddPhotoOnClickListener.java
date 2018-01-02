package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AddPhotoOnClickListener implements View.OnClickListener {
    private AddMedicineActivity addMedicineActivity;
    private Medicine medicine;

    private String mCurrentPhotoPath;

    private Uri createdPhotoUri;

    public AddPhotoOnClickListener(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        this.addMedicineActivity = addMedicineActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        boolean isAllowedToTakePicture = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (isAllowedToTakePicture) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                File photoFile = preparePhotoFile();
                Uri photoURI = Uri.fromFile(photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Log.i(getClass().getSimpleName(), "Picture saved to: " + mCurrentPhotoPath);

                medicine.setThumbnailUri(mCurrentPhotoPath);
                addMedicineActivity.startActivityForResult(takePictureIntent, AppConstants.REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(context, R.string.camera_required, Toast.LENGTH_SHORT).show();
        }
    }

    private File preparePhotoFile() {
        File medicinesPhotosDir = new File(addMedicineActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), AppConstants.MEDICINE_PHOTOS_DIR);

        if (!medicinesPhotosDir.exists()) {
            medicinesPhotosDir.mkdir();
        }

        String fileName = String.valueOf(new Random().nextInt()) + ".jpg";

        File photoFile = new File(medicinesPhotosDir, fileName);

        try {
            photoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentPhotoPath = photoFile.getAbsolutePath();

        return photoFile;
    }
}
