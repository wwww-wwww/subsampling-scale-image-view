package com.davemorrissey.labs.subscaleview.provider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provider from an already open input stream.
 * <p>
 * Please keep in mind this provider is not reusable.
 */
public class OpenStreamProvider implements InputProvider {

    private final InputStream stream;

    public OpenStreamProvider(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public InputStream openStream() throws IOException {
        return stream;
    }
}
