package com.streamlio.api.sink;

import com.streamlio.io.*;

public interface Sink<T extends WriteResult, U extends WriteContext, V extends WriteIO> {

    /**
     *
     * @param Context
     * @return
     */
    Writeable<T,U> open(V Context);

}
