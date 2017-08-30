package com.streamlio.api.source;

import com.streamlio.io.ReadIO;
import com.streamlio.io.ReadResult;
import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;

public abstract class RichSource<T extends ReadResult, U extends ReaderContext, V extends ReadIO> extends
        BasicSource<T,U,V> implements Readable {

    public RichSource(final V context){
        super(context);
    }
}
