package lmnp.wirtualnaapteczka.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Utils related to capturing and displaying photos.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public class PhotoUtils {
    private PhotoUtils() {
    }

    public static void takePicture(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(addMedicineActivity.getPackageManager()) != null) {
            File photoFile = preparePhotoFile(addMedicineActivity);
            String photoAbsolutePath = photoFile.getAbsolutePath();
            Uri photoURI = Uri.fromFile(photoFile);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            Log.i(PhotoUtils.class.getSimpleName(), "Picture saved to: " + photoAbsolutePath);

            medicine.setOldThumbnailToDelete(medicine.getThumbnailUri());
            medicine.setThumbnailUri(photoAbsolutePath);
            addMedicineActivity.startActivityForResult(takePictureIntent, AppConstants.REQUEST_IMAGE_CAPTURE);
        }
    }

    public static void deleteThumbnailFile(String thumbnailUri) {
        File file = new File(thumbnailUri);

        if (file.exists()) {
            boolean hasFileBeenDeleted = file.delete();

            if (hasFileBeenDeleted) {
                Log.i(PhotoUtils.class.getSimpleName(), "File(" + thumbnailUri + ") has been successfully deleted.");
            } else {
                Log.w(PhotoUtils.class.getSimpleName(), "Could not delete file: " + thumbnailUri);
            }
        }
    }

    public static Bitmap prepareBitmap(String photoUrl, ImageView imageView) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        if (targetW == 0 || targetH == 0) {
            targetH = 200;
            targetW = 200;
        }

        BitmapFactory.decodeFile(photoUrl, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(photoUrl, bmOptions);
        int rotateBy = getRotationAngleFromExif(photoUrl);

        if (rotateBy != 0) {
            bitmap = rotateBitmapByDegrees(bitmap, rotateBy);
        }

        return bitmap;
    }

    private static Bitmap rotateBitmapByDegrees(Bitmap bitmap, int rotateBy) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateBy);

        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    public static int getRotationAngleFromExif(String photoFilePath) {
        ExifInterface exif;

        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            Log.e(PhotoUtils.class.getSimpleName(), "Unable to prepare ExifInterface for file: " + photoFilePath);
            return 0;
        }

        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle;

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotationAngle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotationAngle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotationAngle = 270;
                break;
            default:
                rotationAngle = 0;
        }

        return rotationAngle;
    }

    private static File preparePhotoFile(AddMedicineActivity addMedicineActivity) {
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

        return photoFile;
    }

}
