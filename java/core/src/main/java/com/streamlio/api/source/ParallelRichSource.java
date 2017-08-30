package com.streamlio.api.source;

import com.streamlio.io.ReadIO;
import com.streamlio.io.ReadResult;
import com.streamlio.io.ReaderContext;

public abstract class ParallelRichSource<T extends ReadResult, U extends ReaderContext, V extends ReadIO>
        extends BasicSource<T,U,V> {

    int parallelism;

    public ParallelRichSource(final int parallelism, final V context){
        super(context);
    }

}
