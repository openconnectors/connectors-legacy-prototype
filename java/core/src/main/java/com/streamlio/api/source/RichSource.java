package com.streamlio.api.source;

import com.streamlio.io.ReadIO;

public abstract class RichSource extends BasicSource {

    public RichSource(final ReadIO context){
        super(context);
    }
}
