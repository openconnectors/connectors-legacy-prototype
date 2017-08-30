package com.streamlio.api.sink;

import com.streamlio.io.*;

public interface Sink<T extends WriteResult, U extends WriterContext, V extends WriteIO> {

    /**
     *
     * @param context
     * @return
     */
    Writeable<T,U> open(final V context);

    boolean isOpen();

}
