package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SinkConnectorContext;
import org.streamlio.util.SinkTaskConfig;

public abstract class SourceContextSinkLinked
        <T extends SinkConnectorContext, U extends Message, V extends Message>
        implements SourceContext<V> {

    public SinkConnector<T,U> getSink() {
        return sink;
    }

    private SinkConnector<T,U> sink;

    public SourceContextSinkLinked(SinkConnector<T,U> sink){
        this.sink = sink;
    }

}
