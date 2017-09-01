package com.streamlio.sink;

import com.streamlio.config.Config;
import com.streamlio.io.WriterContext;
import com.streamlio.io.WriteResult;
import com.streamlio.io.Writeable;

public abstract class RichSink<T extends WriteResult, U extends WriterContext, V extends Config>
        extends BasicSink<T,U,V> implements Writeable {

    private V config;

    public RichSink(V config) {
        super(config);
    }
}
