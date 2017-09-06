package com.streamlio.heron;

import com.streamlio.message.Message;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Tuple;

import java.io.Serializable;

public interface TupleToMessageMapper extends Serializable {

    /**
     * Convert tuple to {@link com.streamlio.message.Message}.
     *
     * @param tuple
     * @return
     */
    Message toMessage(Tuple tuple);

    /**
     * Declare the output schema for the bolt.
     *
     * @param declarer
     */
    void declareOutputFields(OutputFieldsDeclarer declarer);
}