/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.streamlio.heron;

import org.streamlio.config.MapConfig;
import org.streamlio.connect.SinkConnector;
import org.streamlio.message.Message;
import com.twitter.heron.api.bolt.BaseRichBolt;
import com.twitter.heron.api.bolt.OutputCollector;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;

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
