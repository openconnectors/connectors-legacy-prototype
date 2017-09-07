package com.streamlio.localfs;

import com.streamlio.heron.SourceSpoutAdapter;

public class LineSpout extends SourceSpoutAdapter<LocalFSSource,LineValueMapper,LineDataMessage> {

    public LineSpout() {
        super(new LocalFSSource(), new LineValueMapper());
    }
}
