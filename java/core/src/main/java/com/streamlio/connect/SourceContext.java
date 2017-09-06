package com.streamlio.connect;

import com.streamlio.config.Config;
import com.streamlio.message.Message;

import java.io.Closeable;
import java.util.Collection;

public interface SourceContext<W extends Message, X extends Config> extends Closeable {

    void setup(X config) throws Exception;
    void collect(Collection<W> messages) throws Exception;
}
