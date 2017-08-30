package com.streamlio.api.sink;

import com.streamlio.io.WriterContext;
import com.streamlio.io.WriteResult;
import com.streamlio.io.Writeable;
import com.streamlio.io.WriteIO;

public abstract class RichSink<T extends WriteResult, U extends WriterContext, V extends WriteIO>
        extends BasicSink<T,U,V> implements Writeable {

    public RichSink(final V context){
        super(context);
    }
}
