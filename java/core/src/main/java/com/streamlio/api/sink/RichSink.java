package com.streamlio.api.sink;

import com.streamlio.io.WriteIO;

public abstract class RichSink extends BasicSink {

    public RichSink(final WriteIO context){
        super(context);
    }
}
