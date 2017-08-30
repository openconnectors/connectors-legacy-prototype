package com.streamlio.io;

public interface Writeable<T extends WriteResult, U extends WriteContext> {

    /**
     *
     * @param context
     * @return
     */
    T write(final U context);
}
