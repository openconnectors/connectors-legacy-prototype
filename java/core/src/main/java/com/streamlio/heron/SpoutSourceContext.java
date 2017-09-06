package com.streamlio.heron;

import com.streamlio.config.Config;
import com.streamlio.connect.SourceContext;
import com.streamlio.message.Message;
import com.twitter.heron.api.spout.SpoutOutputCollector;

import java.io.IOException;
import java.util.Collection;

public class SpoutSourceContext implements SourceContext {

    private SpoutOutputCollector collector;
    private MessageToValuesMapper messageMapper;

    public <U extends MessageToValuesMapper> SpoutSourceContext(SpoutOutputCollector collector, U messageMapper) {
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
