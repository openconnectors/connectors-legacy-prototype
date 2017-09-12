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

package org.streamlio.connectors.pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.message.BaseMessage;
import org.streamlio.util.SinkConnectorContext;

import java.io.IOException;
import java.util.Collection;

public class PulsarSink extends SinkConnector<SinkConnectorContext,BaseMessage> {

    private PulsarClient client;
    private Producer producer;

    @Override
    public void publish(Collection<BaseMessage> messages) throws Exception {
        for(BaseMessage message : messages){
            producer.send(message.getData());
        }
    }

    @Override
    public void flush() throws Exception {

    }

    @Override
    public void open(Config config) throws Exception {

        this.client = PulsarClient.create(config.getString(ConfigKeys.KEY_SERVICE_URL));
        this.producer = client.createProducer(
                config.getString(ConfigKeys.PRODUCER_URL));

    }

    @Override
    public void close() throws IOException {
        this.producer.close();
        this.client.close();

    }

    @Override
    public String getVersion() {
        return PulsarConnectorVersion.getVersion();
    }
}
