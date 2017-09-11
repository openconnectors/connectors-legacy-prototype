package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;

import java.io.Closeable;
import java.io.Serializable;
import java.util.Collection;

public interface SourceContext<T extends Message> extends Closeable, Serializable {

    void setup(Config config) throws Exception;
    void collect(Collection<T> messages) throws Exception;
}
