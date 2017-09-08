package org.streamlio.heron;

import org.streamlio.config.MapConfig;
import org.streamlio.connect.SourceConnector;
import org.streamlio.message.Message;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;

import java.util.HashMap;
import java.util.Map;

public abstract class SourceSpoutAdapter<T extends SourceConnector, U extends MessageToValuesMapper, V extends Message>
        extends BaseRichSpout implements IMetric {

    private T source;
    private U messageMapper;
    private SpoutSourceContext<V> sourceContext;


    public SourceSpoutAdapter(T source, U messageMapper){
        this.source = source;
        this.messageMapper = messageMapper;
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
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        try {
            final MapConfig config = new MapConfig((HashMap<String, Object>) conf);
            source.open(config);
            sourceContext = new SpoutSourceContext(collector, messageMapper);
            sourceContext.setup(config);
            source.start(sourceContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {

    }
}
