package com.streamlio.api.sink;

import com.streamlio.io.WriterContext;
import com.streamlio.io.WriteIO;
import com.streamlio.io.WriteResult;

import java.io.Closeable;

/**
 *
 */
public abstract class BasicSink<T extends WriteResult, U extends WriterContext, V extends WriteIO>
        implements Sink<T,U,V>, Closeable{

    public BasicSink(final V context){}

}
