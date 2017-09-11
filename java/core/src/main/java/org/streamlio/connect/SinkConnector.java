package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SinkTaskConfig;
import org.streamlio.util.SinkConnectorContext;

import java.util.Collection;

public abstract class SinkConnector
        <T extends SinkConnectorContext, U extends Message>
        extends Connector<T> {

    public abstract void publish(final Collection<U> messages) throws Exception;

    public abstract void flush() throws Exception;

}
