package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SinkConnectorContext;
import org.streamlio.util.SinkTaskConfig;

public abstract class SourceContextSinkLinked
        <T extends SinkConnectorContext, U extends Message, V extends Message>
        implements SourceContext<U> {

    public SinkConnector<T,V> getSink() {
        return sink;
    }

    private SinkConnector<T,V> sink;

    public SourceContextSinkLinked(SinkConnector<T,V> sink){
        this.sink = sink;
    }

}
