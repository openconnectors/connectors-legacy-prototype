package com.streamlio.stream;

import com.google.common.primitives.Longs;
import com.streamlio.config.Config;
import com.streamlio.connect.SinkConnector;
import com.streamlio.message.Message;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

public class PrintStreamSink extends SinkConnector<SinkTaskConfig,SinkConnectorContext,Config,Message> {

    private String outputFormat;

    private PrintStream stream = System.out;

    @Override
    public void publish(Collection<Message> messages) throws Exception {
        for(Message message : messages){
            final String output = String.format(
                    outputFormat,
                    Longs.fromByteArray(message.getMessageId().toByteArray()),
                    new String(message.getData()));
            stream.println(output);
        }
    }

    @Override
    public void flush() throws Exception {
        stream.flush();
    }

    @Override
    public void open(Config config) throws Exception {
        outputFormat = config.getString(ConfigKeys.OUTPUT_FORMAT_KEY, ConfigKeys.DEFAULT_OUTPUT_FORMAT);
        stream = System.out;
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public String getVersion() {
        return StdStreamConVer.getVersion();
    }
}
