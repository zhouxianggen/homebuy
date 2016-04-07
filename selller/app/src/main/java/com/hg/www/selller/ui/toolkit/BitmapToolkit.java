package com.hg.www.selller.ui.toolkit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.hg.www.selller.GlobalContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapToolkit {
    private static final String TAG = BitmapToolkit.class.getSimpleName();

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(
            String file, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }

    public static Bitmap decodeSampledBitmapFromUrl(
            String url, int reqWidth, int reqHeight) {

        Log.d(TAG, String.format("decodeSampledBitmapFromUrl [%s]", url));
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = null;
        try{
            URL ulrn = new URL(url);

            BitmapFactory.decodeStream(ulrn.openConnection().getInputStream(), null, options);
            Log.d(TAG, String.format("image width=[%d], height=[%d]", options.outWidth, options.outHeight));

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(ulrn.openConnection().getInputStream(), null, options);
            if (bitmap == null) {
                Log.d(TAG, "decode result is null");
            }
        } catch(Exception e) {
            Log.e(TAG, e.toString());
        }

        return bitmap;
    }

    public static String compress(String inPath, int dstWidth, int dstHeight) {
        try {
            InputStream in = new FileInputStream(inPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            int inWidth = options.outWidth;
            int inHeight = options.outHeight;

            in = new FileInputStream(inPath);
            options = new BitmapFactory.Options();
            options.inSampleSize = calculateInSampleSize(options, dstWidth, dstHeight);
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap,
                    (int) (roughBitmap.getWidth() * values[0]),
                    (int) (roughBitmap.getHeight() * values[4]), true);

            File outputDir = GlobalContext.getInstance().getCacheDir();
            String filename = inPath.substring(inPath.lastIndexOf("/") + 1);
            String outPath = File.createTempFile("tmp", ".jpg", outputDir).getPath();
            Log.d(TAG, String.format("tmp output file is [%s]", outPath));
            FileOutputStream out = new FileOutputStream(outPath);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return outPath;
        }
        catch (IOException e) {
            Log.e(TAG, String.format("exception: [%s]", e.toString()));
            return null;
        }
    }
}
