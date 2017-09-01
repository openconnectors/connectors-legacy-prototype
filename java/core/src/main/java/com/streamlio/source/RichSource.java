package com.streamlio.source;

import com.streamlio.config.Config;
import com.streamlio.io.ReadResult;
import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;

public abstract class RichSource<T extends ReadResult, U extends ReaderContext, V extends Config> extends
        BasicSource<T,U,V> implements Readable {

    public RichSource(final V config){
        super(config);
    }
}
