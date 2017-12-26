package lmnp.wirtualnaapteczka.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

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
        Bitmap rotatedBitmap = rotateBitmapBy90degrees(bitmap);

        return rotatedBitmap;
    }

    private static Bitmap rotateBitmapBy90degrees(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }
}
