package com.streamlio.localfs;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.context.CopyContext;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

public class FSCopyContext extends
        CopyContext<LineDataMessage,SinkTaskConfig,SinkConnectorContext,MapConfig> {


    public FSCopyContext(SinkConnector sink) {
        super(sink);
    }

}
