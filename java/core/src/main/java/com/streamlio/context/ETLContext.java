package com.streamlio.context;

import com.streamlio.config.Config;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.connect.SourceContextSinkLinked;
import com.streamlio.message.Message;
import com.streamlio.runner.Mapper;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

import java.io.IOException;
import java.util.Collection;

public class ETLContext
        <T extends Message,U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config,
                X extends Message>
        extends SourceContextSinkLinked<T,U,V,W,X> {

    private Mapper<T,X> mapper;

    public ETLContext(SinkConnector<U,V,W,X> sink, Mapper<T,X> mapper) {
        super(sink);
        this.mapper = mapper;
    }

    @Override
    public void setup(W config) throws Exception {
        mapper.setup(config);
        this.getSink().open(config);
    }

    @Override
    public void collect(Collection<T> messages) throws Exception {
        this.getSink().publish(mapper.transform(messages));

    }

    @Override
    public void close() throws IOException {
        mapper.close();
        this.getSink().close();
    }

}
