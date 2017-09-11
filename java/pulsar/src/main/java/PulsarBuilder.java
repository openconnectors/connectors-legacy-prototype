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

import org.apache.pulsar.client.api.ClientConfiguration;
import org.apache.pulsar.client.api.ProducerConfiguration;
import org.streamlio.common.util.Utils;
import org.streamlio.connectors.pulsar.PulsarBolt;
import org.streamlio.connectors.pulsar.TupleToMessageMapper;

public class PulsarBuilder {

    public static class ProducerBuilder {
        private String serviceUrl;
        private String topic;
        private ClientConfiguration clientConfiguration;
        private ProducerConfiguration producerConfiguration;
        private TupleToMessageMapper tupleToMessageMapper;
        private int metricsTimeIntervalInSeconds;

        @SuppressWarnings("HiddenField")
        public ProducerBuilder setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
            return this;
        }

        @SuppressWarnings("HiddenField")
        public ProducerBuilder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        @SuppressWarnings("HiddenField")
        public ProducerBuilder setClientConfiguration(
                ClientConfiguration clientConfiguration) {
            this.clientConfiguration = clientConfiguration;
            return this;
        }

        @SuppressWarnings("HiddenField")
        public ProducerBuilder setProducerConfiguration(
                ProducerConfiguration producerConfiguration) {
            this.producerConfiguration = producerConfiguration;
            return this;
        }

        @SuppressWarnings("HiddenField")
        public ProducerBuilder setTupleToMessageMapper(
                TupleToMessageMapper tupleToMessageMapper) {
            this.tupleToMessageMapper = tupleToMessageMapper;
            return this;
        }

        public ProducerBuilder setMetricsTimeInterval(int seconds) {
            metricsTimeIntervalInSeconds = seconds;
            return this;
        }

        public PulsarBolt build() {
            Utils.checkNotNull(serviceUrl, "A service url must be provided");
            Utils.checkNotNull(topic, "A topic must be provided");
            Utils.checkNotNull(tupleToMessageMapper,
                    "A TupleToMessageMapper must be provided");
            return new PulsarBolt(this);
        }
    }
}
