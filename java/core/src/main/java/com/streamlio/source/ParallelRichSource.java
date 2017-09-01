package com.streamlio.source;

import com.streamlio.config.Config;
import com.streamlio.io.ReadResult;
import com.streamlio.io.ReaderContext;

public abstract class ParallelRichSource<T extends ReadResult, U extends ReaderContext, V extends Config>
        extends BasicSource<T,U,V> {

    int parallelism;

    public ParallelRichSource(final int parallelism, final V config){
        super(config);
        this.parallelism = parallelism;
    }

}
