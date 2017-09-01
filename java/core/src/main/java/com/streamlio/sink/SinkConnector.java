package com.streamlio.sink;

import com.streamlio.config.Config;
import com.streamlio.util.TaskConfig;
import com.streamlio.io.SinkContext;
import com.streamlio.message.SinkMessage;

import java.util.Collection;

public abstract class SinkConnector<T extends TaskConfig, U extends SinkContext, V extends Config>{

    public abstract void publish(final Collection<SinkMessage> messages);

    public abstract void flush();

}
