package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

public class AddPhotoOnClickListener implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private AddMedicineActivity addMedicineActivity;
    private Medicine medicine;

    public AddPhotoOnClickListener(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        this.addMedicineActivity = addMedicineActivity;
        this.medicine = medicine;
    }

    @Override
    public void onClick(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        addMedicineActivity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }
}
