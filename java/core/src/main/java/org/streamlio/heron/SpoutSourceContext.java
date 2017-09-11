package org.streamlio.heron;

import org.streamlio.config.Config;
import org.streamlio.connect.SourceContext;
import org.streamlio.message.Message;
import com.twitter.heron.api.spout.SpoutOutputCollector;

import java.io.IOException;
import java.util.Collection;

public class SpoutSourceContext<T extends Message> implements SourceContext<T> {

    private SpoutOutputCollector collector;
    private MessageToValuesMapper<T> messageMapper;

    public SpoutSourceContext(SpoutOutputCollector collector, MessageToValuesMapper<T> messageMapper) {
        this.collector = collector;
        this.messageMapper = messageMapper;
    }

    @Override
    public void setup(Config config) throws Exception {

    }

    @Override
    public void collect(Collection<T> messages) throws Exception {
        for(T message : messages){
            collector.emit(messageMapper.toValues((message)));
        }
    }

    @Override
    public void close() throws IOException {

    }
}
