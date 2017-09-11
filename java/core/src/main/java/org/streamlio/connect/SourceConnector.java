package org.streamlio.connect;

import org.streamlio.config.Config;
import org.streamlio.message.Message;
import org.streamlio.util.SourceConnectorContext;
import org.streamlio.util.SourceTaskConfig;

import java.util.Collection;

public abstract class SourceConnector
        <T extends SourceConnectorContext, U extends Message>
        extends Connector<T> implements Task<SourceContext<U>>{

    public abstract Collection<U> poll() throws Exception;

}
