package com.streamlio.connect;

import com.streamlio.config.Config;
import com.streamlio.message.Message;
import com.streamlio.util.SinkTaskConfig;
import com.streamlio.util.SinkConnectorContext;

import java.io.Serializable;
import java.util.Collection;

public abstract class SinkConnector
        <T extends SinkTaskConfig, U extends SinkConnectorContext, V extends Config, W extends Message>
        extends Connector<T, U, V> {

    public abstract void publish(final Collection<W> messages) throws Exception;

    public abstract void flush() throws Exception;

}
