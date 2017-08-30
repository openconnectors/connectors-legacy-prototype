package com.streamlio.runner;

import com.streamlio.io.ReadResult;
import com.streamlio.io.WriterContext;

import java.io.Closeable;

public abstract class Mapper<T extends ReadResult, U extends WriterContext> implements Closeable{

    abstract public void setup();

    abstract public U transform(T readResult);

}
