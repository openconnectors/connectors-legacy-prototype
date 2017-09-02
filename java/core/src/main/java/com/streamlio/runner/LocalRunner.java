package com.streamlio.runner;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.localfs.FSCopyContext;
import com.streamlio.localfs.LocalFSSource;
import com.streamlio.localfs.LocalFSSink;

import java.util.HashMap;

public class LocalRunner extends BasicRunner {

    public LocalRunner(SourceConnector source, SourceContext sourceContext, SinkConnector sink) {
        super(source, sourceContext, sink);
    }

    public static void main(String[] args) throws Exception {

          LocalRunner runner = new LocalRunner(new LocalFSSource(), new FSCopyContext(new LocalFSSink()), new LocalFSSink());

          HashMap<String, Object> props = new HashMap<>();

          MapConfig config = new MapConfig(props);

          runner.setup(config);

          runner.run();

//        LocalRunner lr = new LocalRunner(
//                "/Users/a.ahmed/Desktop/test.txt",
//                "/Users/a.ahmed/Desktop/test_out.txt");
//
//        lr.setup();
//        lr.start();
//        lr.close();
    }


}
