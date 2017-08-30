package com.streamlio.io;

import java.io.Closeable;

public interface Writeable<T extends WriteResult, U extends WriterContext> extends Closeable {

    /**
     *
     * @param context
     * @return
     */
    T write(final U context);
}
