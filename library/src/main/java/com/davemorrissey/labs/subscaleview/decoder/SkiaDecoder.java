package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.provider.InputProvider;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Default implementation of {@link com.davemorrissey.labs.subscaleview.decoder.Decoder}
 * using Android's {@link android.graphics.BitmapRegionDecoder}, based on the Skia library. This
 * works well in most circumstances and has reasonable performance due to the cached decoder instance,
 * however it has some problems with grayscale, indexed and CMYK images.
 * <p>
 * A {@link ReadWriteLock} is used to delegate responsibility for multi threading behaviour to the
 * {@link BitmapRegionDecoder} instance on SDK &gt;= 21, whilst allowing this class to block until no
 * tiles are being loaded before recycling the decoder. In practice, {@link BitmapRegionDecoder} is
 * synchronized internally so this has no real impact on performance.
 */
public class SkiaDecoder implements Decoder {

    private final ReadWriteLock decoderLock = new ReentrantReadWriteLock(true);
    private final Bitmap.Config bitmapConfig;
    private BitmapRegionDecoder decoder;

    @Keep
    @SuppressWarnings("unused")
    public SkiaDecoder() {
        this(null);
    }

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public SkiaDecoder(@Nullable Bitmap.Config bitmapConfig) {
        Bitmap.Config globalBitmapConfig = SubsamplingScaleImageView.getPreferredBitmapConfig();
        if (bitmapConfig != null) {
            this.bitmapConfig = bitmapConfig;
        } else if (globalBitmapConfig != null) {
            this.bitmapConfig = globalBitmapConfig;
        } else {
            this.bitmapConfig = Bitmap.Config.RGB_565;
        }
    }

    @Override
    @NonNull
    public Point init(Context context, @NonNull InputProvider provider) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = provider.openStream();
            decoder = BitmapRegionDecoder.newInstance(inputStream, false);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) { /* Ignore */ }
            }
        }
        if (decoder == null) {
            throw new Exception("Skia decoder failed to initialize and get image size");
        }
        return new Point(decoder.getWidth(), decoder.getHeight());
    }

    @Override
    @NonNull
    public Bitmap decodeRegion(@NonNull Rect sRect, int sampleSize) {
        getDecodeLock().lock();
        try {
            if (decoder != null && !decoder.isRecycled()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = sampleSize;
                options.inPreferredConfig = bitmapConfig;

                Bitmap bitmap = decoder.decodeRegion(sRect, options);

                if (bitmap == null) {
                    throw new RuntimeException("Skia image decoder returned null bitmap - image format may not be supported");
                }
                return bitmap;
            } else {
                throw new IllegalStateException("Cannot decode region after decoder has been recycled");
            }
        } finally {
            getDecodeLock().unlock();
        }
    }

    @Override
    public synchronized boolean isReady() {
        return decoder != null && !decoder.isRecycled();
    }

    @Override
    public synchronized void recycle() {
        decoderLock.writeLock().lock();
        try {
            decoder.recycle();
            decoder = null;
        } finally {
            decoderLock.writeLock().unlock();
        }
    }

    private Lock getDecodeLock() {
        return decoderLock.readLock();
    }
}
