package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.previewphotoactivity.GoBackOnClickListener;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.ThumbnailUtils;

/**
 * Activity responsible for handling events on the layout for previewing photo of the medicine.
 *
 * @author Sebastian Nowak
 * @createdAt 28.12.2017
 */
public class PreviewPhotoActivity extends AppCompatActivity {
    private ImageView photoImage;
    private Button backBtn;

    private String photoUri;
    private Class<? extends AppCompatActivity> invokingClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);

        initializeMembers();

        backBtn.setOnClickListener(new GoBackOnClickListener(invokingClass));

        Bitmap bitmap = ThumbnailUtils.prepareBitmap(photoUri, photoImage);
        photoImage.setImageBitmap(bitmap);
    }

    private void initializeMembers() {
        photoImage = (ImageView) findViewById(R.id.photo_image);
        backBtn = (Button) findViewById(R.id.back_btn);

        Intent intent = getIntent();
        photoUri = intent.getStringExtra(AppConstants.MEDICINE_PHOTO_URI);
        invokingClass = (Class<? extends AppCompatActivity>) intent.getSerializableExtra(AppConstants.INVOKING_CLASS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_undo, menu);
        return true;
    }


}