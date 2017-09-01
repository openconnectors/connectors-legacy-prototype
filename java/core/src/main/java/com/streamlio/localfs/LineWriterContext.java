package com.streamlio.localfs;

import com.streamlio.io.SinkContext;

public class LineWriterContext implements SinkContext {

    private final String data;

    public LineWriterContext(final String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
