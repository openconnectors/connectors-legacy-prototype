package org.streamlio.heron;

import org.streamlio.message.Message;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Tuple;

import java.io.Serializable;

public interface TupleToMessageMapper<T extends Message> extends Serializable {

    /**
     * Convert tuple to {@link Message}.
     *
     * @param tuple
     * @return
     */
    T toMessage(Tuple tuple);

    /**
     * Declare the output schema for the bolt.
     *
     * @param declarer
     */
    void declareOutputFields(OutputFieldsDeclarer declarer);
}