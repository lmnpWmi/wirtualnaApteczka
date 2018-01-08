package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;

public class AddPhotoOnClickListener implements View.OnClickListener {
    private AddMedicineActivity addMedicineActivity;
    private Medicine medicine;
    private Class<? extends AppCompatActivity> addMedicineInvokingClass;

    public AddPhotoOnClickListener(AddMedicineActivity addMedicineActivity, Medicine medicine, Class<? extends AppCompatActivity> addMedicineInvokingClass) {
        this.addMedicineActivity = addMedicineActivity;
        this.medicine = medicine;
        this.addMedicineInvokingClass = addMedicineInvokingClass;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        boolean isAllowedToTakePicture = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        PhotoDescriptionTO photoDescriptionTO = medicine.getPhotoDescriptionTO();

        boolean arePhotosPhysicallyPresentOnDevice = PhotoUtils.arePhotosPhysicallyPresentOnDevice(photoDescriptionTO);

        if (!photoDescriptionTO.isEmpty() && arePhotosPhysicallyPresentOnDevice) {
            AlertDialogPreparator.showTakeNewPictureOrViewExistingDialog(addMedicineActivity, medicine, addMedicineInvokingClass);
        } else {
            if (isAllowedToTakePicture) {
                PhotoUtils.takePicture(addMedicineActivity, medicine);
            } else {
                Toast.makeText(context, R.string.camera_required, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
