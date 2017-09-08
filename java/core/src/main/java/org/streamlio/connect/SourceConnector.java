package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SourceConnectorContext;
import org.streamlio.util.SourceTaskConfig;

import java.util.Collection;

public abstract class SourceConnector
        <T extends SourceTaskConfig, U extends SourceConnectorContext, V extends Config, W extends Message>
        extends Connector<T,U,V> implements Task<SourceContext<W,V>>{

    public abstract Collection<W> poll() throws Exception;

}
