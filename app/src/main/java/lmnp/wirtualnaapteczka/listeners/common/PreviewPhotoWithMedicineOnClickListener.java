package lmnp.wirtualnaapteczka.listeners.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;

/**
 * Listener for previewing medicine photo in full size that allows to additionally pass back medicine data to re-instantiate previous view.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
public class PreviewPhotoWithMedicineOnClickListener extends PreviewPhotoOnClickListener {
    private Medicine medicine;
    private Class<? extends AppCompatActivity> addMedicineInvokingClass;

    public PreviewPhotoWithMedicineOnClickListener(Medicine medicine, Class<? extends AppCompatActivity> invokingClass, Class<? extends AppCompatActivity> addMedicineInvokingClass) {
        super(medicine.getThumbnailUri(), invokingClass);
        this.medicine = medicine;
        this.addMedicineInvokingClass = addMedicineInvokingClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = prepareIntent(v);
        intent.putExtra(AppConstants.MEDICINE, medicine);
        intent.putExtra(AppConstants.ADD_MEDICINE_INVOKING_CLASS, addMedicineInvokingClass);

        v.getContext().startActivity(intent);
    }
}
