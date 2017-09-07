package examples.legacy;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SourceConnector;
import org.apache.storm.metric.api.IMetric;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;

import java.util.HashMap;
import java.util.Map;

public abstract class SourceSpoutAdapterLegacy<T extends SourceConnector, U extends MessageToValuesMapperLegacy>
        extends BaseRichSpout implements IMetric {

    private T source;
    private U messageMapper;
    private SpoutSourceContextLegacy sourceContext;


    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        try {
            final MapConfig config = new MapConfig((HashMap<String, Object>) map);
            source.open(config);
            sourceContext = new SpoutSourceContextLegacy(collector, messageMapper);
            sourceContext.setup(config);
            source.start(sourceContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValueAndReset() {
        return null;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        messageMapper.declareOutputFields(declarer);
    }

    @Override
    public void nextTuple() {

    }
}