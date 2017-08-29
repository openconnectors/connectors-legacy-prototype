package com.streamlio.api.source;

import com.streamlio.io.ReadIO;

import java.io.Closeable;

public abstract class BasicSource implements Source, Closeable{

    public BasicSource(final ReadIO context){}
}
