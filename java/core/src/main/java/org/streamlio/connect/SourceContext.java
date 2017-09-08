package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;

import java.io.Closeable;
import java.io.Serializable;
import java.util.Collection;

public interface SourceContext<W extends Message, X extends Config> extends Closeable, Serializable {

    void setup(X config) throws Exception;
    void collect(Collection<W> messages) throws Exception;
}
