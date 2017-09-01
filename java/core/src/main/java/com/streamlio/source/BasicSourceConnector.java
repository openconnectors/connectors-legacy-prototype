package com.streamlio.source;

import com.streamlio.config.Config;
import com.streamlio.io.ReadResult;
import com.streamlio.io.ReaderContext;

import java.io.Closeable;

public abstract class BasicSourceConnector<T extends ReadResult, U extends ReaderContext, V extends Config>
        implements SourceConnector<T,U,V>, Closeable{

    private V config;

    public BasicSourceConnector(final V config){
        this.config = config;
    }

    public V getConfig(){
        return config;
    }
}
