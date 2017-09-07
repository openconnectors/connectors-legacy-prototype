package examples.legacy;

import com.streamlio.message.Message;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.io.Serializable;

public interface TupleToMessageMapperLegacy extends Serializable {

    /**
     * Convert tuple to {@link Message}.
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