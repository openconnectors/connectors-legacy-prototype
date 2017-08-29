package com.streamlio.api.sink;

import com.streamlio.io.WriteIO;

import java.io.Closeable;

/**
 *
 */
public abstract class BasicSink implements Sink, Closeable{

    public BasicSink(final WriteIO context){}

}
