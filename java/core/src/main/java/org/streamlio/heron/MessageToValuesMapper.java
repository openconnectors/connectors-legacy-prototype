package org.streamlio.heron;

import org.streamlio.message.Message;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Values;

import java.io.Serializable;

public interface MessageToValuesMapper<T extends Message> extends Serializable {

    /**
     * Convert {@link Message} to tuple values.
     *
     * @param message
     * @return
     */
    Values toValues(T message);

    /**
     * Declare the output schema for the spout.
     *
     * @param declarer
     */
    void declareOutputFields(OutputFieldsDeclarer declarer);
}
