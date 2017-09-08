package examples.legacy;

import org.streamlio.config.Config;
import org.streamlio.connect.SourceContext;
import org.streamlio.message.Message;
import org.apache.storm.spout.SpoutOutputCollector;

import java.io.IOException;
import java.util.Collection;

public class SpoutSourceContextLegacy implements SourceContext {

    private SpoutOutputCollector collector;
    private MessageToValuesMapperLegacy messageMapper;

    public <U extends MessageToValuesMapperLegacy> SpoutSourceContextLegacy(SpoutOutputCollector collector, U messageMapper) {
        this.collector = collector;
        this.messageMapper = messageMapper;
    }

    @Override
    public void setup(Config config) throws Exception {

    }

    @Override
    public void collect(Collection messages) throws Exception {
        for(Object message : messages){
            collector.emit(messageMapper.toValues((Message)message));
        }
    }

    @Override
    public void close() throws IOException {

    }
}
