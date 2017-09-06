package com.streamlio.stream;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.connect.SourceContextSinkLinked;
import com.streamlio.context.CopyContext;
import com.streamlio.runner.BasicRunner;
import com.streamlio.runner.LinkedBasicRunner;

import java.util.HashMap;

public class LocalStreamCopyRunner extends LinkedBasicRunner {

    public LocalStreamCopyRunner(SourceConnector source, SourceContextSinkLinked sourceContext) {
        super(source, sourceContext);
    }

    public static void main(String[] args) throws Exception {

        PrintStreamSink sink = new PrintStreamSink();

        LocalStreamCopyRunner runner = new LocalStreamCopyRunner(
                new StdInputStreamSource(),
                new CopyContext(sink)
        );

        HashMap<String, Object> props = new HashMap<>();
        MapConfig config = new MapConfig(props);
        runner.setup(config);
        runner.run();
    }
}
