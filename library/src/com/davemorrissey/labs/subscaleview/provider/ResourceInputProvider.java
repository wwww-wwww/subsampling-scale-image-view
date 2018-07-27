package com.davemorrissey.labs.subscaleview.provider;

import android.content.Context;
import android.content.res.Resources;

import java.io.InputStream;

public class ResourceInputProvider implements InputProvider {

  private Resources resources;
  public final int resource;

  public ResourceInputProvider(Context context, int resource) {
    this.resources = context.getResources();
    this.resource = resource;
  }

  @Override
  public InputStream openStream() {
    return resources.openRawResource(resource);
  }
}
