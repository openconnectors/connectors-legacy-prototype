package com.streamlio.stream;

import com.streamlio.config.Config;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.localfs.LineDataMessage;
import com.streamlio.message.Message;
import com.streamlio.util.SourceConnectorContext;
import com.streamlio.util.SourceTaskConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class StdInpStreamSource extends SourceConnector<SourceTaskConfig,SourceConnectorContext,Config,Message> {

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
    public void start(SourceContext<Message, Config> ctx) throws Exception {

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
