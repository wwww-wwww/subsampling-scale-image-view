package com.davemorrissey.labs.subscaleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.davemorrissey.labs.subscaleview.provider.AssetInputProvider;
import com.davemorrissey.labs.subscaleview.provider.InputProvider;
import com.davemorrissey.labs.subscaleview.provider.OpenStreamProvider;
import com.davemorrissey.labs.subscaleview.provider.ResourceInputProvider;
import com.davemorrissey.labs.subscaleview.provider.UriInputProvider;

import java.io.InputStream;

/**
 * Helper class used to set the source and additional attributes from a variety of sources. Supports
 * use of a bitmap, asset, resource, external file or any other URI.
 * <p>
 * When you are using a preview image, you must set the dimensions of the full size image on the
 * ImageSource object for the full size image using the {@link #dimensions(int, int)} method.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class ImageSource {

    private final Bitmap bitmap;
    private final InputProvider provider;
    private int sWidth;
    private int sHeight;
    private Rect sRegion;
    private boolean cached;

    private ImageSource(Bitmap bitmap, boolean cached) {
        this.bitmap = bitmap;
        this.provider = null;
        this.sWidth = bitmap.getWidth();
        this.sHeight = bitmap.getHeight();
        this.cached = cached;
    }

    private ImageSource(InputProvider provider) {
        this.bitmap = null;
        this.provider = provider;
    }

    /**
     * Create an instance from a resource. The correct resource for the device screen resolution will be used.
     *
     * @param resId resource ID.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource resource(Context context, int resId) {
        return new ImageSource(new ResourceInputProvider(context, resId));
    }

    /**
     * Create an instance from an asset name.
     *
     * @param assetName asset name.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource asset(Context context, @NonNull String assetName) {
        //noinspection ConstantConditions
        if (assetName == null) {
            throw new NullPointerException("Asset name must not be null");
        }
        return new ImageSource(new AssetInputProvider(context, assetName));
    }

    /**
     * Create an instance from a URI.
     *
     * @param uri image URI.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource uri(Context context, @NonNull Uri uri) {
        //noinspection ConstantConditions
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        return new ImageSource(new UriInputProvider(context, uri));
    }

    /**
     * Create an instance from an input provider.
     *
     * @param provider input stream provider.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource provider(@NonNull InputProvider provider) {
        //noinspection ConstantConditions
        if (provider == null) {
            throw new NullPointerException("Input provider must not be null");
        }
        return new ImageSource(provider);
    }

    /**
     * Create an instance from an input stream.
     *
     * @param stream open input stream.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource inputStream(@NonNull InputStream stream) {
        //noinspection ConstantConditions
        if (stream == null) {
            throw new NullPointerException("Input stream must not be null");
        }
        return new ImageSource(new OpenStreamProvider(stream));
    }

    /**
     * Provide a loaded bitmap for display.
     *
     * @param bitmap bitmap to be displayed.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource bitmap(@NonNull Bitmap bitmap) {
        //noinspection ConstantConditions
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        }
        return new ImageSource(bitmap, false);
    }

    /**
     * Provide a loaded and cached bitmap for display. This bitmap will not be recycled when it is no
     * longer needed. Use this method if you loaded the bitmap with an image loader such as Picasso
     * or Volley.
     *
     * @param bitmap bitmap to be displayed.
     * @return an {@link ImageSource} instance.
     */
    @NonNull
    public static ImageSource cachedBitmap(@NonNull Bitmap bitmap) {
        //noinspection ConstantConditions
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        }
        return new ImageSource(bitmap, true);
    }

    /**
     * Use a region of the source image. Region must be set independently for the full size image and the preview if
     * you are using one.
     *
     * @param sRegion the region of the source image to be displayed.
     * @return this instance for chaining.
     */
    @NonNull
    public ImageSource region(Rect sRegion) {
        this.sRegion = sRegion;
        setInvariants();
        return this;
    }

    /**
     * Declare the dimensions of the image. This is only required for a full size image, when you are specifying a URI
     * and also a preview image. When displaying a bitmap object, or not using a preview, you do not need to declare
     * the image dimensions. Note if the declared dimensions are found to be incorrect, the view will reset.
     *
     * @param sWidth  width of the source image.
     * @param sHeight height of the source image.
     * @return this instance for chaining.
     */
    @NonNull
    public ImageSource dimensions(int sWidth, int sHeight) {
        if (bitmap == null) {
            this.sWidth = sWidth;
            this.sHeight = sHeight;
        }
        setInvariants();
        return this;
    }

    private void setInvariants() {
        if (this.sRegion != null) {
            this.sWidth = this.sRegion.width();
            this.sHeight = this.sRegion.height();
        }
    }

    InputProvider getProvider() {
        return provider;
    }

    Bitmap getBitmap() {
        return bitmap;
    }

    int getSWidth() {
        return sWidth;
    }

    int getSHeight() {
        return sHeight;
    }

    Rect getSRegion() {
        return sRegion;
    }

    boolean isCached() {
        return cached;
    }
}
