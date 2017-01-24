package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;

import com.hippo.image.BitmapDecoder;
import com.hippo.image.BitmapRegionDecoder;

import java.io.InputStream;

public class IImageRegionDecoder implements ImageRegionDecoder {

    private BitmapRegionDecoder decoder = null;

    @Override
    public Point init(Context context, Uri uri) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            decoder = BitmapRegionDecoder.newInstance(inputStream);
        } finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (Exception e) { }
            }
        }
        return new Point(decoder.getWidth(), decoder.getHeight());
    }

    @Override
    public Bitmap decodeRegion(Rect sRect, int sampleSize) {
        int ratio = 1 + (int)(Math.log(sampleSize) / Math.log(2));

        Bitmap bitmap = decoder.decodeRegion(sRect, BitmapDecoder.CONFIG_RGB_565, ratio);
        if (bitmap != null) {
            return bitmap;
        } else {
            throw new RuntimeException("Null region bitmap");
        }
    }

    @Override
    public boolean isReady() {
        return decoder != null && !decoder.isRecycled();
    }

    @Override
    public void recycle() {
        decoder.recycle();
    }
}
