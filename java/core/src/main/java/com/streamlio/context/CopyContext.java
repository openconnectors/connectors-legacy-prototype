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

//public class CopyContext
//        <T extends Message,U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config>
//        implements SourceContext<T> {
//
//    private SinkConnector<U,V,W,T> sink;
//
//    public CopyContext(SinkConnector<U,V,W,T> sink){
//        this.sink = sink;
//    }
//
//    @Override
//    public void collect(Collection<T> messages) throws Exception{
//        sink.publish(messages);
//    }
//
//    @Override
//    public void close() throws IOException {
//        sink.close();
//    }
//}

public class CopyContext
        <T extends Message,U extends SinkTaskConfig, V extends SinkConnectorContext, W extends Config>
    extends ETLContext<T,U,V,W,T>{

    public CopyContext(SinkConnector<U, V, W, T> sink, Mapper<T, T> mapper) {
        super(sink, mapper);
    }
}


