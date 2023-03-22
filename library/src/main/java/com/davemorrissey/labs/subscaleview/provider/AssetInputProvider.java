package com.davemorrissey.labs.subscaleview.provider;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class AssetInputProvider implements InputProvider {

    public final String assetName;
    private final AssetManager assets;

    public AssetInputProvider(Context context, String assetName) {
        this.assets = context.getAssets();
        this.assetName = assetName;
    }

    @Override
    public InputStream openStream() throws IOException {
        return assets.open(assetName);
    }
}
