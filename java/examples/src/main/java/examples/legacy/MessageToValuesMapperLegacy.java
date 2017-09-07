package examples.legacy;

import com.streamlio.message.Message;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Values;

import java.io.Serializable;

public interface MessageToValuesMapperLegacy extends Serializable {

    /**
     * Convert {@link Message} to tuple values.
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
