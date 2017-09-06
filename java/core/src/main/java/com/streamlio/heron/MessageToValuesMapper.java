package com.streamlio.heron;

import com.streamlio.message.Message;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Values;

import java.io.Serializable;

public interface MessageToValuesMapper extends Serializable {

    /**
     * Convert {@link com.streamlio.message.Message} to tuple values.
     *
     * @param message
     * @return
     */
    Values toValues(Message message);

    /**
     * Declare the output schema for the spout.
     *
     * @param declarer
     */
    void declareOutputFields(OutputFieldsDeclarer declarer);
}
