package org.streamlio.stream;

import org.streamlio.config.Config;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;
import org.streamlio.localfs.LineDataMessage;
import org.streamlio.message.Message;
import org.streamlio.util.SourceConnectorContext;
import org.streamlio.util.SourceTaskConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class StdInputStreamSource extends SourceConnector<SourceConnectorContext,Message> {

    private InputStream stream;
    private InputStreamReader streamReader;
    private BufferedReader bufferedReader;
    private AtomicLong linesRead;

    @Override
    public Collection poll() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public void open(Config config) throws Exception {
        stream = System.in;
        streamReader = new InputStreamReader(stream);
        bufferedReader = new BufferedReader(streamReader);
        linesRead = new AtomicLong(0);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public void start(SourceContext<Message> ctx) throws Exception {

        while (true) {
            Thread.sleep(10);
            if (bufferedReader.ready()) {
                long id = linesRead.incrementAndGet();
                ctx.collect(Collections.singleton(
                        new LineDataMessage(id, bufferedReader.readLine())));
            }
        }

    }

    @Override
    public void stop() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public String getVersion() {
        return StdStreamConVer.getVersion();
    }
}
