package com.streamlio.context;

import com.streamlio.config.Config;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.message.Message;
import com.streamlio.runner.Mapper;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

import java.io.IOException;
import java.util.Collection;

public class ETLContext
        <T extends Message,U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config, X extends Message>
        implements SourceContext<T> {

    private SinkConnector<U,V,W,X> sink;
    private Mapper<T,X> mapper;

    public ETLContext(SinkConnector<U,V,W,X> sink, Mapper<T,X> mapper){
        this.sink = sink;
        this.mapper = mapper;
    }

    @Override
    public void collect(Collection<T> messages) throws Exception{
        sink.publish(mapper.transform(messages));
    }

    @Override
    public void close() throws IOException {
        mapper.close();
        sink.close();
    }

}
