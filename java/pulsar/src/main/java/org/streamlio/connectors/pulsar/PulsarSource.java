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

import org.apache.commons.lang3.SerializationUtils;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.streamlio.config.Config;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;
import org.streamlio.message.ByteableBaseMessage;
import org.streamlio.util.SourceConnectorContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class PulsarSource extends SourceConnector<SourceConnectorContext, String> {

    private PulsarClient client;
    private Consumer consumer;


    @Override
    public Collection<String> poll() throws Exception {
        return null;
    }

    @Override
    public void open(Config config) throws Exception {

        this.client = PulsarClient.create(config.getString(ConfigKeys.SERVICE_URL));
        this.consumer = client.subscribe(
                config.getString(ConfigKeys.CONSUMER_URL),
                config.getString(ConfigKeys.SUBSCRIPTION_NAME));

    }

    @Override
    public void close() throws IOException {
        this.consumer.close();
        this.client.close();
    }

    @Override
    public void start(SourceContext<String> ctx) throws Exception {
        while (true) {
            Message msg = consumer.receive();
            ctx.collect(
                    Collections.singleton(
                            new String(msg.getData()))
            );
            consumer.acknowledge(msg);
        }
    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public String getVersion() {
        return PulsarConnectorVersion.getVersion();
    }
}
