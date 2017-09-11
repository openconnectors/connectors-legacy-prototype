package examples.localfs;

import org.streamlio.config.Config;
import org.streamlio.config.ConfigProvider;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContextSinkLinked;
import org.streamlio.context.CopyContext;
import org.streamlio.localfs.LocalFSSink;
import org.streamlio.localfs.LocalFSSource;
import org.streamlio.runner.LinkedBasicRunner;

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

        Config config = new ConfigProvider();
        runner.setup(config);
        runner.run();
    }
}
