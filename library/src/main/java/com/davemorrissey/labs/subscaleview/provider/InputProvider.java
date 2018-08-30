package com.davemorrissey.labs.subscaleview.provider;

import java.io.IOException;
import java.io.InputStream;

public interface InputProvider {

    InputStream openStream() throws IOException;
}
