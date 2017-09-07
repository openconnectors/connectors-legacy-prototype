package com.streamlio.localfs;

import com.streamlio.heron.MessageToValuesMapper;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Values;

public class LineValueMapper implements MessageToValuesMapper<LineDataMessage>{

    @Override
    public Values toValues(LineDataMessage message) {
        return new Values(message.getMessageId(), message.getData());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
