package com.streamlio.localfs;

import com.streamlio.heron.SinkBoltAdapter;

public class LineBolt extends SinkBoltAdapter{

    public LineBolt() {
        super(new LocalFSSink(), new LineTupleMapper());
    }
}
