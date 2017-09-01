package com.streamlio.localfs;

import com.streamlio.source.RichSource;
import com.streamlio.config.MapConfig;
import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LineSource extends RichSource<StringLineReadResult, ReaderContext, MapConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);
    private Readable<StringLineReadResult, ReaderContext> reader;

    public LineSource(MapConfig config) {
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
