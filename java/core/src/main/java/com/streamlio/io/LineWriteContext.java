package com.streamlio.io;

public class LineWriteContext implements WriteContext{

    private String data;

    public LineWriteContext(final String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
