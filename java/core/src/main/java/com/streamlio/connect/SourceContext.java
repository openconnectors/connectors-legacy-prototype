package com.streamlio.connect;

import com.streamlio.message.Message;

import java.io.Closeable;
import java.util.Collection;

public interface SourceContext<W extends Message> extends Closeable {

    void collect(Collection<W> messages) throws Exception;

}
