package com.davemorrissey.labs.subscaleview.decoder;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;

import com.hippo.image.BitmapDecoder;
import com.hippo.image.BitmapRegionDecoder;
import com.hippo.image.ImageInfo;

import java.io.InputStream;

public class IImageRegionDecoder implements ImageRegionDecoder {

    private BitmapRegionDecoder decoder = null;

    @Override
    public Point init(Context context, Uri uri) throws Exception {
        ImageInfo info = new ImageInfo();
        ContentResolver resolver = context.getContentResolver();

        InputStream is = null;
        try {
            is = resolver.openInputStream(uri);
            boolean result = BitmapDecoder.decode(is, info);
            if (!result) {
                throw new RuntimeException("Image info returned false");
            }
        } finally {
            if (is != null) {
                try { is.close(); } catch (Exception e) { /* Empty */ }
            }
        }

        decoder = BitmapRegionDecoder.newInstance(resolver.openInputStream(uri));
        return new Point(info.width, info.height);
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
