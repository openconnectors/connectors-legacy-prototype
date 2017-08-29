package com.streamlio.io;

public interface Writable {

    /**
     *
     * @param write
     * @return
     */
    WriteResult write(WriteContext write);
}
