package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SinkConnectorContext;
import org.streamlio.util.SinkTaskConfig;

public abstract class SourceContextSinkLinked
        <T extends Message, U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config, X extends Message>
        implements SourceContext<T,W> {

    public SinkConnector<U, V, W, X> getSink() {
        return sink;
    }

    private SinkConnector<U,V,W,X> sink;

    public SourceContextSinkLinked(SinkConnector<U,V,W,X> sink){
        this.sink = sink;
    }

}
