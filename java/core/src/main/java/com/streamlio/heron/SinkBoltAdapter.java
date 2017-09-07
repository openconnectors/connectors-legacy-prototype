package com.streamlio.heron;

import com.streamlio.config.MapConfig;
import com.streamlio.connect.SinkConnector;
import com.streamlio.message.Message;
import com.twitter.heron.api.bolt.BaseRichBolt;
import com.twitter.heron.api.bolt.OutputCollector;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;
import com.twitter.heron.api.tuple.Values;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SinkBoltAdapter<T extends SinkConnector, U extends TupleToMessageMapper>
        extends BaseRichBolt implements IMetric {

    private T sink;
    private U tupleMapper;


    public SinkBoltAdapter(T sink, U tupleMapper){
        this.sink = sink;
        this.tupleMapper = tupleMapper;
    }

    @Override
    public Object getValueAndReset() {
        return null;
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "line"));

    }

    @Override
    public void prepare(Map<String, Object> heronConf, TopologyContext context, OutputCollector collector) {
        try {
            sink.open(new MapConfig((HashMap<String, Object>) heronConf));
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
