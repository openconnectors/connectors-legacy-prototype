package com.streamlio.io;

import java.io.Closeable;

public interface Writeable<T extends WriteResult, U extends SinkContext> extends Closeable {

//    /**
//     *
//     * @param context
//     * @return
//     */
//    T write(final U context);
//
//    boolean isOpen();
}
