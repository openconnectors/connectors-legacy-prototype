package org.streamlio.context;

import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.connect.SourceContextSinkLinked;
import org.streamlio.message.Message;
import org.streamlio.runner.Mapper;
import org.streamlio.util.SinkConnectorContext;
import org.streamlio.util.SinkTaskConfig;

import java.io.IOException;
import java.util.Collection;

public class ETLContext
        <T extends SinkConnectorContext,U extends Message,V extends Message>
        extends SourceContextSinkLinked<T,U,V> {

    private Mapper<U,V> mapper;

    public ETLContext(SinkConnector<T,V> sink, Mapper<U,V> mapper) {
        super(sink);
        this.mapper = mapper;
    }

    @Override
    public void setup(Config config) throws Exception {
        mapper.setup(config);
        this.getSink().open(config);
    }

    @Override
    public void collect(Collection<U> messages) throws Exception {
        this.getSink().publish(mapper.transform(messages));

    }

    @Override
    public void close() throws IOException {
        mapper.close();
        this.getSink().close();
    }

}
