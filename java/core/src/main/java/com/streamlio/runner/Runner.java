package com.streamlio.runner;

import com.streamlio.sink.Sink;
import com.streamlio.source.Source;

import java.io.Closeable;

public abstract class Runner<T extends Source, U extends Mapper, V extends Sink>  implements Closeable {

    T source;
    U mapper;
    V sink;

    abstract public void setup();

    abstract public void start();

}
