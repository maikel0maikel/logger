package com.maikel.logger.utils;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    private static final String TAG = "IOUtils";

    private IOUtils() {
    }

    /**
     * Closes a <code>Closeable</code> unconditionally.
     *
     * @param closeable the objects to close, may be null or already closed
     * @since 2.0
     */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        }
    }
}
