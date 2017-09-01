package com.streamlio.localfs;

import com.streamlio.config.MapConfig;
import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LineFSSource extends SourceConnector {

    private static final Logger LOG = LoggerFactory.getLogger(LocalFSSink.class);
    private Readable<StringLineReadResult, ReaderContext> reader;

    public LineFSSource(MapConfig config) {
        super(config);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public void initialize() {
        this.reader = new LocalFileReader(this.getConfig().getString(ConfigKeys.INPUT_FILEPATH_KEY));
        LOG.info("Initialized : " + this.getVersion());
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }

//    @Override
//    public boolean isOpen() {
//        return reader.isOpen();
//    }
//
//    @Override
//    public StringLineReadResult query(ReaderContext context) {
//        return this.reader.query(context);
//    }
}
