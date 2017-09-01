//package com.streamlio.localfs;
//
//import com.streamlio.config.Config;
//import com.streamlio.config.MapConfig;
//import com.streamlio.io.WriteResult;
//import com.streamlio.io.Writeable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.streamlio.sink.SinkConnector;
//
//import java.io.IOException;
//
//public class LocalFSSink extends SinkConnector<WriteResult,LineWriterContext,MapConfig> {
//
//    private static final Logger LOG = LoggerFactory.getLogger(LocalFSSink.class);
//    private Writeable writer;
//
//    public LocalFSSink(MapConfig config) {
//        super(config);
//    }
//
//    @Override
//    public void initialize() {
//        this.writer = new LocalFileWriter(this.getConfig().getString(ConfigKeys.INPUT_FILEPATH_KEY));
//        LOG.info("Initialized : " + this.getVersion());
//    }
//
//    @Override
//    public String version() {
//        return null;
//    }
//
//    @Override
//    public void open(Config config) {
//
//    }
//
//    @Override
//    public void close() throws IOException {
//        this.writer.close();
//        LOG.info("Closed : " + this.getVersion());
//    }
//
//    @Override
//    public String getVersion() {
//        return LocalFSConVer.getVersion();
//    }
//
//    @Override
//    public void processMessage(WriteResult message) throws IOException {
//
//    }
//
//    @Override
//    public void commit() throws Exception {
//
//    }
//
////    @Override
////    public boolean isOpen() {
////        return writer.isOpen();
////    }
//
//
//
////    @Override
////    public WriteResult write(SinkContext context) {
////        return this.writer.write(context);
////    }
//}
