package com.streamlio.io;

public interface Writeable<T extends WriteResult, U extends WriteContext> {

    /**
     *
     * @param write
     * @return
     */
    T write(final U write);
}
