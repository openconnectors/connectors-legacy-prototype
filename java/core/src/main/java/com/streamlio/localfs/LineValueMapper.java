package com.streamlio.localfs;

import com.google.common.primitives.Longs;
import com.streamlio.heron.MessageToValuesMapper;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Values;

public class LineValueMapper implements MessageToValuesMapper<LineDataMessage>{

    @Override
    public Values toValues(LineDataMessage message) {
        return new Values(
                Longs.fromByteArray(message.getMessageId().toByteArray()),
                new String(message.getData())
        );
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "line"));

    }
}
