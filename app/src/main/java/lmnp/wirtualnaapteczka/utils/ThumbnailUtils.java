package lmnp.wirtualnaapteczka.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Prepares bitmaps/thumbnails for layouts.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public class ThumbnailUtils {

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
            Log.e(ThumbnailUtils.class.getSimpleName(), "Unable to prepare ExifInterface for file: " + photoFilePath);
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

}