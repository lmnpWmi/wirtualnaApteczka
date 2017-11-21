package lmnp.wirtualnaapteczka.listeners;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class AddMedicinePhotoListener implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private AddActivity addActivity;
    private Medicine medicine;

    public AddMedicinePhotoListener(AddActivity addActivity, Medicine medicine) {
        this.addActivity = addActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        addActivity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }
}
