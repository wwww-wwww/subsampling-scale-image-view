package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;

import com.davemorrissey.labs.subscaleview.provider.InputProvider;
import com.hippo.image.BitmapDecoder;

import java.io.InputStream;

public class IImageDecoder implements ImageDecoder {

    @Override
    public Bitmap decode(Context context, InputProvider provider) throws Exception {
        InputStream is = null;
        Bitmap bitmap;
        try {
            is = provider.openStream();
            bitmap = BitmapDecoder.decode(is, BitmapDecoder.CONFIG_RGB_565);
        } finally {
            if (is != null) {
                try { is.close(); } catch (Exception e) { /* Empty */ }
            }
        }

        if (bitmap == null) {
            throw new Exception("Image decoder returned null bitmap");
        }
        return bitmap;
    }
}
