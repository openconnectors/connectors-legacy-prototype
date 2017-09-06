package com.streamlio.localfs;

import com.streamlio.config.PropertiesConfig;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContextSinkLinked;
import com.streamlio.context.CopyContext;
import com.streamlio.runner.LinkedBasicRunner;

public class LocalFSCopyRunner extends LinkedBasicRunner {

    public LocalFSCopyRunner(SourceConnector source, SourceContextSinkLinked sourceContext) {
        super(source, sourceContext);
    }

    public static void main(String[] args) throws Exception {

        LocalFSSink sink = new LocalFSSink();

        LocalFSCopyRunner runner = new LocalFSCopyRunner(
                new LocalFSSource(),
                new CopyContext(sink)
        );

        PropertiesConfig config = new PropertiesConfig();
        runner.setup(config);
        runner.run();
    }
}
