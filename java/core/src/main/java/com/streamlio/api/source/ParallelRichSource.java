package com.streamlio.api.source;

import com.streamlio.io.ReadIO;

public abstract class ParallelRichSource extends BasicSource {

    int parallelism;

    public ParallelRichSource(final int parallelism, final ReadIO context){
        super(context);
    }

}
