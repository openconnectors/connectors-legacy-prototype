package com.streamlio.util;

import java.io.Closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();

                // Suppress it since we ignore any exceptions
                // SUPPRESS CHECKSTYLE IllegalCatch
            } catch (Exception e) {
                LOG.warn("Failed to close {}", closeable, e);
            }
        }
    }

    static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }

        return reference;
    }

    static <T> T defaultIfNull(T reference, T defaultValue) {
        return reference != null ? reference : defaultValue;
    }

    private Utils() {}
}
