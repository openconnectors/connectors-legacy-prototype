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
import org.streamlio.connect.SourceConnector;
import org.streamlio.message.BaseMessage;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;

import java.util.HashMap;
import java.util.Map;

public abstract class SourceSpoutAdapter<T extends SourceConnector, U extends MessageToValuesMapper, V extends BaseMessage>
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
