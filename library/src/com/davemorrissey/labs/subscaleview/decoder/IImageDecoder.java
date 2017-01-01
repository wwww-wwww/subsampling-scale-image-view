package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.hippo.image.BitmapDecoder;

import java.io.InputStream;

public class IImageDecoder implements ImageDecoder {

    @Override
    public Bitmap decode(Context context, Uri uri) throws Exception {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
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
