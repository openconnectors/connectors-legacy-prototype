package org.streamlio.io;

import org.streamlio.util.SinkConnectorContext;

import java.io.Closeable;

public interface Writeable<T extends WriteResult, U extends SinkConnectorContext> extends Closeable {

//    /**
//     *
//     * @param context
//     * @return
//     */
//    T write(final U context);
//
//    boolean isOpen();
}
