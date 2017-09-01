package com.streamlio.connect;

import com.streamlio.config.Config;
import com.streamlio.message.Message;
import com.streamlio.util.SourceConnectorContext;
import com.streamlio.util.SourceTaskConfig;

import java.util.Collection;

public abstract class SourceConnector
        <T extends SourceTaskConfig, U extends SourceConnectorContext, V extends Config, W extends Message>
        extends Connector<T,U,V> implements Task<SourceContext<W>>{

//    public abstract void start(final SourceContext<W> ctx) throws Exception;
//
//    public abstract void stop(final SourceContext<W> ctx) throws Exception;

    public abstract Collection<W> poll() throws Exception;

}
