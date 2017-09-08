package org.streamlio.localfs;

import org.streamlio.heron.TupleToMessageMapper;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;

public class LineTupleMapper implements TupleToMessageMapper<LineDataMessage> {

    @Override
    public LineDataMessage toMessage(Tuple tuple) {
        return new LineDataMessage(tuple.getLong(0), tuple.getString(1));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "line"));

    }
}
