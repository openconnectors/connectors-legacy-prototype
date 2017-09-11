package org.streamlio.context;

import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.message.Message;
import org.streamlio.util.SinkConnectorContext;

import java.io.IOException;
import java.util.Collection;

public class CopyContext<T extends SinkConnectorContext, U extends Message>
    extends ETLContext<T,U,V,W,T>{

    public CopyContext(SinkConnector<U, V, W, T> sink) {
        super(sink, null);
    }

    @Override
    public void collect(Collection<U> messages) throws Exception{
        this.getSink().publish(messages);
    }

    @Override
    public void setup(Config config) throws Exception {
        this.getSink().open(config);
    }

    @Override
    public void close() throws IOException {
        this.getSink().close();
    }
}


