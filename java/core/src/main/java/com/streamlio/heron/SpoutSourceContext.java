package com.streamlio.heron;

import com.streamlio.config.Config;
import com.streamlio.connect.SourceContext;
import com.streamlio.message.Message;
import com.twitter.heron.api.spout.SpoutOutputCollector;

import java.io.IOException;
import java.util.Collection;

public class SpoutSourceContext<T extends Message> implements SourceContext<T,Config> {

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
