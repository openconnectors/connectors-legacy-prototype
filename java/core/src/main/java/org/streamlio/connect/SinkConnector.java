package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SinkTaskConfig;
import org.streamlio.util.SinkConnectorContext;

import java.util.Collection;

public abstract class SinkConnector
        <T extends SinkTaskConfig, U extends SinkConnectorContext, V extends Config, W extends Message>
        extends Connector<T, U, V> {

    public abstract void publish(final Collection<W> messages) throws Exception;

    public abstract void flush() throws Exception;

}
