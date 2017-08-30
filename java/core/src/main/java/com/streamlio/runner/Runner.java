package com.streamlio.runner;

import com.streamlio.api.sink.Sink;
import com.streamlio.api.source.Source;

import java.io.Closeable;

public abstract class Runner<T extends Source, U extends Mapper, V extends Sink>  implements Closeable{

    abstract public void setup();

    abstract public void start();
    
}
