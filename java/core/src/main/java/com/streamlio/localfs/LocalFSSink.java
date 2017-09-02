package com.streamlio.localfs;

import com.streamlio.config.Config;
import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.io.WriteResult;
import com.streamlio.io.Writeable;
import com.streamlio.message.Message;
import com.streamlio.util.SinkConnectorContext;
import com.streamlio.util.SinkTaskConfig;
import com.streamlio.util.SourceConnectorContext;
import com.streamlio.util.SourceTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class LocalFSSink extends SinkConnector
        <SinkTaskConfig,SinkConnectorContext,Config,LineDataMessage>{

    private static final Logger LOG = LoggerFactory.getLogger(LocalFSSink.class);

    private BufferedWriter writer;
    private AtomicLong linesWritten;

    @Override
    public void open(Config config) throws Exception {
        writer = new BufferedWriter(new FileWriter(config.getString(ConfigKeys.OUTPUT_FILEPATH_KEY)));
        linesWritten = new AtomicLong(0);
    }

    @Override
    public void publish(Collection<LineDataMessage> lines) throws Exception {
        try {
            for(LineDataMessage message : lines){
                writer.write(message.getData().toString());
                writer.write("\n");
                linesWritten.incrementAndGet();
            }
            writer.flush();

        } catch (IOException e) {
            LOG.error("Problem writing to file, " + linesWritten.get() + " lines written", e);
            throw e;
        }
    }

    @Override
    public void flush() throws Exception {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }
}
