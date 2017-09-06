package com.streamlio.connect;

import com.streamlio.config.Config;
import com.streamlio.message.Message;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

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
