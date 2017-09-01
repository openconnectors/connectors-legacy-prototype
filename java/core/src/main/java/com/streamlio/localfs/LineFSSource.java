package com.streamlio.localfs;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.util.SourceConnectorContext;
import com.streamlio.util.SourceTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class LineFSSource extends SourceConnector
        <SourceTaskConfig,SourceConnectorContext,MapConfig,LineDataMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(LineFSSource.class);

    private BufferedReader reader;
    private AtomicLong linesRead;

    @Override
    public void open(MapConfig config) throws Exception {
        reader = new BufferedReader(new FileReader(
                config.getString(ConfigKeys.INPUT_FILEPATH_KEY)));
        linesRead = new AtomicLong(0);
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }


    @Override
    public void start(SourceContext<LineDataMessage> ctx) throws Exception {
        try {
            String line = reader.readLine();
            if (line != null) {
                long id = linesRead.incrementAndGet();
                ctx.collect(Collections.singletonList(new LineDataMessage(id, line)));
            } else {
                LOG.info("Finished reading file, " + linesRead.get() + " lines read");
                this.close();
                ctx.close();
            }
        } catch (Exception e) {
            LOG.error("Problem reading file, " + linesRead.get() + " lines read", e);
            throw e;
        }
    }

    @Override
    public void stop() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public Collection<LineDataMessage> poll() throws Exception {
        throw new NotImplementedException();
    }

}
