package lmnp.wirtualnaapteczka.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Utils related to capturing and displaying photos.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public class PhotoUtils {
    private static final String DEFAULT_PHOTO_EXTENSION = ".jpg";
    private static final String SMALL_PHOTO_APPENDIX = "_sm";

    private static final int DEFAULT_HEIGHT_FOR_THUMBNAIL = 300;
    private static final int DEFAULT_WIDTH_FOR_THUMBNAIL = 200;

    private PhotoUtils() {
    }

    public static void takePicture(AddMedicineActivity addMedicineActivity, Medicine medicine) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(addMedicineActivity.getPackageManager()) != null) {
            File fullSizePhoto = preparePhotoFile(addMedicineActivity);
            String fullSizePhotoAbsolutePath = fullSizePhoto.getAbsolutePath();
            Uri fullSizePhotoURI = Uri.fromFile(fullSizePhoto);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fullSizePhotoURI);

//            Log.i(PhotoUtils.class.getSimpleName(), "Picture saved to: " + fullSizePhotoAbsolutePath);

            PhotoDescriptionTO photoDescriptionTO = medicine.getPhotoDescriptionTO();
            List<String> oldPhotoUrisToDelete = Arrays.asList(photoDescriptionTO.getFullSizePhotoUri(), photoDescriptionTO.getSmallSizePhotoUri());

            photoDescriptionTO.setOldPhotoUrisToDelete(oldPhotoUrisToDelete);
            photoDescriptionTO.setFullSizePhotoUri(fullSizePhotoAbsolutePath);

            addMedicineActivity.startActivityForResult(takePictureIntent, AppConstants.REQUEST_IMAGE_CAPTURE);
        }
    }

    public static void deleteThumbnailFile(String thumbnailUri) {
        File file = new File(thumbnailUri);

        if (file.exists()) {
            boolean hasFileBeenDeleted = file.delete();

            if (hasFileBeenDeleted) {
//                Log.i(PhotoUtils.class.getSimpleName(), "File(" + thumbnailUri + ") has been successfully deleted.");
            } else {
//                Log.w(PhotoUtils.class.getSimpleName(), "Could not delete file: " + thumbnailUri);
            }
        }
    }

    public static Bitmap prepareBitmap(String photoUrl, ImageView imageView) {
        return prepareBitmap(photoUrl, imageView.getWidth(), imageView.getHeight());
    }

    public static Bitmap prepareBitmap(String photoUrl, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

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

    public static File prepareSmallSizePhotoFile(File fullSizePhoto) {
        String fullSizePhotoAbsolutePath = fullSizePhoto.getAbsolutePath();

        Bitmap smallSizeThumbnailBitmap = prepareBitmap(fullSizePhotoAbsolutePath, DEFAULT_WIDTH_FOR_THUMBNAIL, DEFAULT_HEIGHT_FOR_THUMBNAIL);

        String fullSizePhotoFileName = fullSizePhoto.getName();
        String smallSizePhotoFileName = fullSizePhotoFileName.replace(DEFAULT_PHOTO_EXTENSION, "") + SMALL_PHOTO_APPENDIX + DEFAULT_PHOTO_EXTENSION;

        File parentDirectory = fullSizePhoto.getParentFile();
        File smallSizePhoto = new File(parentDirectory, smallSizePhotoFileName);

        try {
            smallSizePhoto.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(smallSizePhoto);

            smallSizeThumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
//            Log.e(PhotoUtils.class.getSimpleName(), "Unable to prepare a small size thumbnail");
        }

        return smallSizePhoto;
    }

    private static Bitmap rotateBitmapByDegrees(Bitmap bitmap, int rotateBy) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateBy);

        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    private static int getRotationAngleFromExif(String photoFilePath) {
        ExifInterface exif;

        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
//            Log.e(PhotoUtils.class.getSimpleName(), "Unable to prepare ExifInterface for file: " + photoFilePath);
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

        String fileName = UUID.randomUUID().toString() + DEFAULT_PHOTO_EXTENSION;

        File photoFile = new File(medicinesPhotosDir, fileName);

        try {
            photoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoFile;
    }
}
