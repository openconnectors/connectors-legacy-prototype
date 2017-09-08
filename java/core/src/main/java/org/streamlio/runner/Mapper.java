package org.streamlio.runner;

import org.streamlio.config.Config;
import org.streamlio.message.Message;

import java.io.Closeable;
import java.util.Collection;

public abstract class Mapper<T extends Message, U extends Message> implements Closeable {

    abstract public void setup(Config config);

    abstract public Collection<U> transform(Collection<T> inputMessages);

}
