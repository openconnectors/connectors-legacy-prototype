package com.streamlio.localfs;

import com.streamlio.heron.SourceSpoutAdapter;

public class LineSpout extends SourceSpoutAdapter {

    public LineSpout() {
        super(new LocalFSSource(), new LineValueMapper());
    }
}
