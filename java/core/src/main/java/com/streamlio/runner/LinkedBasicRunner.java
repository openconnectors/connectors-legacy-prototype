package com.streamlio.runner;

import com.streamlio.config.Config;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContextSinkLinked;

public class LinkedBasicRunner<T extends SourceConnector, U extends SourceContextSinkLinked, V extends Config>{

    T source;
    U sourceContextLinked;

    public LinkedBasicRunner(T source, U sourceContextLinked){
        this.source = source;
        this.sourceContextLinked = sourceContextLinked;
    }

    public void setup(V config) throws Exception{
        this.source.open(config);
        this.sourceContextLinked.setup(config);
    }

    public void run() throws Exception {
        this.source.start(this.sourceContextLinked);
    }

}
