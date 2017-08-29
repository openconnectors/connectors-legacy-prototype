package com.streamlio.io;

public interface Readable {


    /**
     * 
     * @param context
     * @return
     */
    ReadResult query(final ReaderContext context);
}
