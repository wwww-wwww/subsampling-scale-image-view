package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.davemorrissey.labs.subscaleview.provider.InputProvider;

import java.io.InputStream;

/**
 * Default implementation of {@link com.davemorrissey.labs.subscaleview.decoder.ImageDecoder}
 * using Android's {@link android.graphics.BitmapFactory}, based on the Skia library. This
 * works well in most circumstances and has reasonable performance, however it has some problems
 * with grayscale, indexed and CMYK images.
 */
public class SkiaImageDecoder implements ImageDecoder {

    @Override
    public Bitmap decode(Context context, InputProvider provider) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        InputStream inputStream = null;
        try {
            inputStream = provider.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        } finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (Exception e) { }
            }
        }

        if (bitmap == null) {
            throw new RuntimeException("Skia image region decoder returned null bitmap - image format may not be supported");
        }
        return bitmap;
    }
}
