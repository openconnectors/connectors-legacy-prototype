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

import org.streamlio.config.Config;
import org.streamlio.connect.SourceContext;
import org.streamlio.message.Message;
import com.twitter.heron.api.spout.SpoutOutputCollector;

import java.io.IOException;
import java.util.Collection;

public class SpoutSourceContext<T extends Message> implements SourceContext<T> {

    private SpoutOutputCollector collector;
    private MessageToValuesMapper<T> messageMapper;

    public SpoutSourceContext(SpoutOutputCollector collector, MessageToValuesMapper<T> messageMapper) {
        this.collector = collector;
        this.messageMapper = messageMapper;
    }

    @Override
    public void setup(Config config) throws Exception {

    }

    @Override
    public void collect(Collection<T> messages) throws Exception {
        for(T message : messages){
            collector.emit(messageMapper.toValues((message)));
        }
    }

    @Override
    public void close() throws IOException {

    }
}
