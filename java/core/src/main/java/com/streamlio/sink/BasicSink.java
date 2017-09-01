package com.streamlio.sink;

import com.streamlio.config.Config;
import com.streamlio.io.WriterContext;
import com.streamlio.io.WriteResult;

import java.io.Closeable;

/**
 *
 */
public abstract class BasicSink<T extends WriteResult, U extends WriterContext, V extends Config>
        implements Sink<T,U,V>, Closeable{

    private V config;

    public BasicSink(final V config){
        this.config = config;
    }

    public V getConfig(){
        return config;
    }

}
