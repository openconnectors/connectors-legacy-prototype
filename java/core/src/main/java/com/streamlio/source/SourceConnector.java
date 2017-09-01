package com.streamlio.source;

import com.streamlio.config.Config;
import com.streamlio.connector.Connector;
import com.streamlio.io.ReaderContext;
import com.streamlio.io.ReadResult;
import com.streamlio.io.SinkContext;
import com.streamlio.message.SinkMessage;
import com.streamlio.message.SourceMessage;
import com.streamlio.util.SourceTaskConfig;
import com.streamlio.util.TaskConfig;

import java.util.Collection;

public abstract class SourceConnector<T extends SourceTaskConfig, U extends SinkContext, V extends Config>
        extends Connector<T, U, V> {

    public abstract Collection<SourceMessage> poll();

}
