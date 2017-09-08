package org.streamlio.runner;
import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;

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
