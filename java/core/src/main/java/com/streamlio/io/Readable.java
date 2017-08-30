package com.streamlio.io;

public interface Readable<T extends ReadResult, U extends ReaderContext> {


    /**
     *
     * @param context
     * @return
     */
    T query(final U context);
}
