package com.streamlio.runner;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;

import java.io.Closeable;

public abstract class BasicRunner<T extends SourceConnector, U extends SourceContext, V extends SinkConnector>  implements Closeable {

    T source;
    U sourceContext;
    V sink;

    public BasicRunner(T source, U sourceContext, V sink){
        this.source = source;
        this.sourceContext = sourceContext;
        this.sink = sink;
    }

    public void setup(){
        this.source.initialize(null);
        this.sink.initialize(null);
        this.sourceContext.

    }

    public void run() throws Exception {
        this.source.start(this.sourceContext);
    }

}
