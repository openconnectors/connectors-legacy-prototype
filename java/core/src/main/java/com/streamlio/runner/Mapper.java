package com.streamlio.runner;

import com.streamlio.io.ReadResult;
import com.streamlio.util.SinkConnectorContext;

import java.io.Closeable;

public abstract class Mapper<T extends ReadResult, U extends SinkConnectorContext> implements Closeable{

    abstract public void setup();

    abstract public U transform(T readResult);

}
