package com.streamlio.context;

import com.streamlio.config.Config;
import com.streamlio.connect.SinkConnector;
import com.streamlio.message.Message;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

import java.util.Collection;

public class CopyContext
        <T extends Message,U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config>
    extends ETLContext<T,U,V,W,T>{

    public CopyContext(SinkConnector<U, V, W, T> sink) {
        super(sink, null);
    }

    @Override
    public void collect(Collection<T> messages) throws Exception{
        this.sink.publish(messages);
    }
}


