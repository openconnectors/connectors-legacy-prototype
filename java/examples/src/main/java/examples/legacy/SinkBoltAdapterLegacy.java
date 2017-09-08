package examples.legacy;

import org.streamlio.config.MapConfig;
import org.streamlio.connect.SinkConnector;
import org.streamlio.message.Message;
import org.apache.storm.metric.api.IMetric;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SinkBoltAdapterLegacy<T extends SinkConnector, U extends TupleToMessageMapperLegacy>
        extends BaseRichBolt implements IMetric {

    private T sink;
    private U tupleMapper;

    @Override
    public Object getValueAndReset() {
        return null;
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        try {
            sink.open(new MapConfig((HashMap<String, Object>) stormConf));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple input) {
        Message message = tupleMapper.toMessage(input);
        try {
            sink.publish(Collections.singleton(message));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
