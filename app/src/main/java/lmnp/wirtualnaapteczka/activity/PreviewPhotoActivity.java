package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;

/**
 * Activity responsible for handling events on the layout for previewing photo of the medicine.
 *
 * @author Sebastian Nowak
 * @createdAt 28.12.2017
 */
public class PreviewPhotoActivity extends AppCompatActivity {
    private ImageView photoImage;

    private Medicine medicine;
    private String photoUri;
    private Class<? extends AppCompatActivity> invokingClass;
    private Class<? extends AppCompatActivity> addMedicineInvokingClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);

        initializeMembers();

        Bitmap bitmap = PhotoUtils.prepareBitmap(photoUri, photoImage);
        photoImage.setImageBitmap(bitmap);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.medicine_photo);
        actionBar.show();
    }

    private void initializeMembers() {
        photoImage = (ImageView) findViewById(R.id.photo_image);

        Intent intent = getIntent();
        photoUri = intent.getStringExtra(AppConstants.MEDICINE_PHOTO_URI);
        invokingClass = (Class<? extends AppCompatActivity>) intent.getSerializableExtra(AppConstants.INVOKING_CLASS);
        addMedicineInvokingClass = (Class<? extends AppCompatActivity>) intent.getSerializableExtra(AppConstants.ADD_MEDICINE_INVOKING_CLASS);
        medicine = (Medicine) intent.getSerializableExtra(AppConstants.MEDICINE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), invokingClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppConstants.MEDICINE, medicine);
        intent.putExtra(AppConstants.INVOKING_CLASS, addMedicineInvokingClass);

        getApplicationContext().startActivity(intent);
    }
}