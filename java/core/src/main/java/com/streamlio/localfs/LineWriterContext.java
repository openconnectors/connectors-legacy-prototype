package com.streamlio.localfs;

import com.streamlio.io.WriterContext;

public class LineWriterContext implements WriterContext {

    private final String data;

    public LineWriterContext(final String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
