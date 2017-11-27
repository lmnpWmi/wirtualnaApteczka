package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.io.File;
import java.io.IOException;

public class AddPhotoOnClickListener implements View.OnClickListener {
    private AddMedicineActivity addMedicineActivity;
    private Medicine medicine;

    private Uri createdPhotoUri;

    public AddPhotoOnClickListener(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        this.addMedicineActivity = addMedicineActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        createdPhotoUri = Uri.fromFile(preparePhotoFile());
        medicine.setThumbnailUri(createdPhotoUri.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, createdPhotoUri);

        addMedicineActivity.startActivityForResult(intent, AppConstants.REQUEST_IMAGE_CAPTURE);
    }

    private File preparePhotoFile() {
        File medicinesPhotosDir = new File(addMedicineActivity.getFilesDir(), AppConstants.MEDICINE_PHOTOS);

        if (!medicinesPhotosDir.exists()) {
            medicinesPhotosDir.mkdir();
        }

        String fileName = "plik.png";

        File photoFile = new File(medicinesPhotosDir, fileName);
        try {
            photoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoFile;
    }
}
