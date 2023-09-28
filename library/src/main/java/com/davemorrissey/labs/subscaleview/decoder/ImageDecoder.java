package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.provider.InputProvider;

import java.io.InputStream;

public class ImageDecoder implements Decoder {

    private static final String TAG = ImageDecoder.class.getSimpleName();

    private final Bitmap.Config bitmapConfig;

    private final boolean cropBorders;

    private tachiyomi.decoder.ImageDecoder decoder = null;

    public ImageDecoder(boolean cropBorders) {
        this(null, cropBorders);
    }

    public ImageDecoder(Bitmap.Config bitmapConfig, boolean cropBorders) {
        this.cropBorders = cropBorders;
        Bitmap.Config globalBitmapConfig = SubsamplingScaleImageView.getPreferredBitmapConfig();
        if (bitmapConfig != null) {
            this.bitmapConfig = bitmapConfig;
        } else if (globalBitmapConfig != null) {
            this.bitmapConfig = globalBitmapConfig;
        } else {
            this.bitmapConfig = Bitmap.Config.RGB_565;
        }
    }

    /**
     * Initialise the decoder. When possible, perform initial setup work once in this method. The
     * dimensions of the image must be returned.
     *
     * @param context  Application context. A reference may be held, but must be cleared on recycle.
     * @param provider Provider of the image.
     * @return Dimensions of the image.
     * @throws Exception if initialisation fails.
     */
    @NonNull
    @Override
    public Point init(Context context, @NonNull InputProvider provider) throws Exception {
        try (InputStream inputStream = provider.openStream()) {
            decoder = tachiyomi.decoder.ImageDecoder.Companion.newInstance(inputStream, cropBorders);
        } catch (Exception e) {
            Log.e(TAG, "Failed to init decoder", e);
        }
        if (decoder == null) {
            throw new Exception("Image decoder failed to initialize and get image size");
        }
        return new Point(decoder.getWidth(), decoder.getHeight());
    }

    /**
     * Decode a region of the image with the given sample size. This method is called off the UI
     * thread so it can safely load the image on the current thread. It is called from
     * {@link android.os.AsyncTask}s running in an executor that may have multiple threads, so
     * implementations must be thread safe. Adding <code>synchronized</code> to the method signature
     * is the simplest way to achieve this, but bear in mind the {@link #recycle()} method can be
     * called concurrently.
     *
     * @param sRect      Source image rectangle to decode.
     * @param sampleSize Sample size.
     * @return The decoded region. It is safe to return null if decoding fails.
     */
    @NonNull
    public Bitmap decodeRegion(@NonNull Rect sRect, int sampleSize) {
        boolean config = getImageConfig();

        Bitmap bitmap = decoder.decode(sRect, config, sampleSize, false, null);

        if (bitmap != null) {
            return bitmap;
        } else {
            throw new RuntimeException("Null region bitmap");
        }
    }

    /**
     * Status check. Should return false before initialisation and after recycle.
     *
     * @return true if the decoder is ready to be used.
     */
    public boolean isReady() {
        return decoder != null && !decoder.isRecycled();
    }

    /**
     * This method will be called when the decoder is no longer required. It should clean up any
     * resources still in use.
     */
    public void recycle() {
        decoder.recycle();
    }

    /**
     * Returns image config from subsampling view configuration.
     */
    private boolean getImageConfig() {
        return bitmapConfig == null || !bitmapConfig.equals(Bitmap.Config.ARGB_8888);
    }
}
