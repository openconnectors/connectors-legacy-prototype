package com.streamlio.heron;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.connect.SourceConnector;
import com.streamlio.connect.SourceContext;
import com.streamlio.message.Message;
import com.twitter.heron.api.bolt.BaseRichBolt;
import com.twitter.heron.api.bolt.OutputCollector;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Tuple;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SourceSpoutAdapter<T extends SourceConnector, U extends MessageToValuesMapper>
        extends BaseRichSpout implements IMetric {

    private T source;
    private U messageMapper;
    private SpoutSourceContext sourceContext;

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
