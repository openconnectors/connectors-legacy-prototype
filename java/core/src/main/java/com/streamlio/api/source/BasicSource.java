package com.streamlio.api.source;

import com.streamlio.io.ReadIO;
import com.streamlio.io.ReadResult;
import com.streamlio.io.ReaderContext;

import java.io.Closeable;

public abstract class BasicSource<T extends ReadResult, U extends ReaderContext, V extends ReadIO>
        implements Source<T,U,V>, Closeable{

    public BasicSource(final V context){}
}
