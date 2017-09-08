package org.streamlio.localfs;

import org.streamlio.heron.SinkBoltAdapter;

public class LineBolt extends SinkBoltAdapter<LocalFSSink,LineTupleMapper> {

    public LineBolt() {
        super(new LocalFSSink(), new LineTupleMapper());
    }
}
