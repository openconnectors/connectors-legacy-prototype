package com.streamlio.connect;

public abstract class SourceContextSinkLinked<> implements SourceContext<> {

    private SinkConnector sink;

    public SourceContextSinkLinked(SinkConnector sink){
        this.sink = sink;
    }

}
