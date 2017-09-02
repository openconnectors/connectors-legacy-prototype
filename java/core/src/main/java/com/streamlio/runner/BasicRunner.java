package com.streamlio.runner;
import com.streamlio.config.Config;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;

public abstract class BasicRunner
        <T extends SourceConnector, U extends SourceContext, V extends SinkConnector, W extends Config> {

    T source;
    U sourceContext;
    V sink;

    public BasicRunner(T source, U sourceContext, V sink){
        this.source = source;
        this.sourceContext = sourceContext;
        this.sink = sink;
    }

    public void setup(W config) throws Exception{
        this.source.open(config);
        this.sink.open(config);
        this.sourceContext.setup(config);
    }

    public void run() throws Exception {
        this.source.start(this.sourceContext);
    }

}
