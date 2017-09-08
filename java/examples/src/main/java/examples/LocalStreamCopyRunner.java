package examples;

import org.streamlio.config.Config;
import org.streamlio.config.ConfigProvider;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContextSinkLinked;
import org.streamlio.context.CopyContext;
import org.streamlio.runner.LinkedBasicRunner;
import org.streamlio.stream.PrintStreamSink;
import org.streamlio.stream.StdInputStreamSource;

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

        Config config = new ConfigProvider();
        runner.setup(config);
        runner.run();
    }
}
