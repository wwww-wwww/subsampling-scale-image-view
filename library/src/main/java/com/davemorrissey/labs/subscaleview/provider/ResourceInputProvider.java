package com.davemorrissey.labs.subscaleview.provider;

import android.content.Context;
import android.content.res.Resources;

import java.io.InputStream;

public class ResourceInputProvider implements InputProvider {

    public final int resource;
    private final Resources resources;

    public ResourceInputProvider(Context context, int resource) {
        this.resources = context.getResources();
        this.resource = resource;
    }

    @Override
    public InputStream openStream() {
        return resources.openRawResource(resource);
    }
}
