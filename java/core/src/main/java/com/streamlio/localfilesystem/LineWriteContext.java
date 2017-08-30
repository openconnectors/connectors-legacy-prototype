package com.streamlio.localfilesystem;

import com.streamlio.io.WriteContext;

public class LineWriteContext extends WriteContext {

    private String data;

    public LineWriteContext(final String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
