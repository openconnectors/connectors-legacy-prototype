package com.streamlio.runner;

import com.streamlio.config.Config;
import com.streamlio.message.Message;

import java.io.Closeable;
import java.util.Collection;

public abstract class Mapper<T extends Message, U extends Message> implements Closeable {

    abstract public void setup(Config config);

    abstract public Collection<U> transform(Collection<T> inputMessages);

}
