package com.streamlio.api.sink;

import com.streamlio.io.Writable;
import com.streamlio.io.WriteIO;

public interface Sink {

    /**
     *
     * @param Context
     * @return
     */
    Writable open(WriteIO Context);

}
