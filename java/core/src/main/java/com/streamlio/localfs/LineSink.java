package com.streamlio.localfs;

import com.streamlio.sink.RichSink;
import com.streamlio.config.MapConfig;
import com.streamlio.io.WriteResult;
import com.streamlio.io.Writeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LineSink extends RichSink<WriteResult,LineWriterContext,MapConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);
    private Writeable writer;

    public LineSink(MapConfig config) {
        super(config);
    }

    @Override
    public void initialize() {
        this.writer = new LocalFileWriter(this.getConfig().getString(ConfigKeys.INPUT_FILEPATH_KEY));
        LOG.info("Initialized : " + this.getVersion());
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
        LOG.info("Closed : " + this.getVersion());
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }

//    @Override
//    public boolean isOpen() {
//        return writer.isOpen();
//    }



//    @Override
//    public WriteResult write(WriterContext context) {
//        return this.writer.write(context);
//    }
}
