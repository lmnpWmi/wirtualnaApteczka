package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.activity.ImageCaptureActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

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
        Intent intent = new Intent(v.getContext(), ImageCaptureActivity.class);
        intent.putExtra(AppConstants.EXISTING_MEDICINE, medicine);

        v.getContext().startActivity(intent);
    }
}
