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

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.twitter.heron.api.bolt.BaseRichBolt;
import com.twitter.heron.api.bolt.OutputCollector;
import com.twitter.heron.api.metric.IMetric;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Tuple;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.PulsarClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.streamlio.common.util.Utils;
import org.streamlio.util.Constants;

public class PulsarBolt extends BaseRichBolt implements IMetric {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(PulsarBolt.class);

    public static final String NO_OF_MESSAGES_SENT = "numberOfMessagesSent";
    public static final String PRODUCER_RATE = "producerRate";
    public static final String PRODUCER_THROUGHPUT_BYTES = "producerThroughput";
    public static final int DEFAULT_MAX_PENDING_MESSAGES = 1000;

    private static final int TIMEOUT_MS = 100;
    private static final int DEFAULT_TIMEOUT_SECS = 60;
    private static final int DEFAULT_METRICS_TIME_INTERVAL_IN_SECS = 60;

    private final ClientConfiguration clientConfiguration;
    private final ProducerConfiguration producerConfiguration;
    private final ConcurrentMap<String, Object> metricsMap = Maps.newConcurrentMap();

    private final String serviceUrl;
    private final String topic;
    private final TupleToMessageMapper tupleToMessageMapper;
    private final int metricsTimeIntervalSeconds;
    private PulsarClient client;
    private String componentId;
    private String boltId;
    private OutputCollector collector;
    private Producer producer;
    private volatile long messagesSent = 0;
    private volatile long messageSizeSent = 0;

    public PulsarBolt(PulsarBuilder.ProducerBuilder builder) {
        serviceUrl = builder.serviceUrl;
        topic = builder.topic;
        tupleToMessageMapper = builder.tupleToMessageMapper;
        clientConfiguration =
                Utils.defaultIfNull(builder.clientConfiguration, getDefaultClientConfiguration());

        producerConfiguration =
                Utils.defaultIfNull(builder.producerConfiguration, getDefaultProducerConfiguration());

        // we must specify a consumer acknowledgement timeout so messages are replayed
        // if one is not set then default to 60 seconds
        if (producerConfiguration.getSendTimeoutMs() == 0) {
            producerConfiguration.setSendTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS);
        }

        metricsTimeIntervalSeconds =
                builder.metricsTimeIntervalInSeconds > 0
                        ? builder.metricsTimeIntervalInSeconds : DEFAULT_METRICS_TIME_INTERVAL_IN_SECS;
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.componentId = context.getThisComponentId();
        this.boltId = String.format("%s-%s", componentId, context.getThisTaskId());
        this.collector = collector;
        try {
            client = createClient();
            producer = client.createProducer(this.topic, this.producerConfiguration);
            LOG.info("[{}] Created a pulsar producer on topic {} to send messages", boltId, topic);
        } catch (PulsarClientException e) {
            LOG.error("[{}] Error initializing pulsar producer on topic {}", boltId, topic, e);
        }
        context.registerMetric(String.format("PulsarBoltMetrics-%s-%s", componentId, context.getThisTaskIndex()), this,
                this.metricsTimeIntervalSeconds);
    }

    PulsarClient createClient()
            throws PulsarClientException {
        return new PulsarClientImpl(serviceUrl, clientConfiguration);
    }

    @Override
    public void execute(Tuple input) {
        // do not send tick tuples since they are used to execute periodic tasks
        if (isTickTuple(input)) {
            collector.ack(input);
            return;
        }
        try {
            if (producer != null) {
                // a message key can be provided in the mapper
                Message msg = this.tupleToMessageMapper.toMessage(input);
                if (msg == null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("[{}] Cannot send null message, acking the collector", boltId);
                    }
                    collector.ack(input);
                } else {
                    final long messageSizeToBeSent = msg.getData().length;
                    producer.sendAsync(msg).handle((r, ex) -> {
                        synchronized (collector) {
                            if (ex != null) {
                                collector.reportError(ex);
                                collector.fail(input);
                                LOG.error("[{}] Message send failed", boltId, ex);

                            } else {
                                collector.ack(input);
                                ++messagesSent;
                                messageSizeSent += messageSizeToBeSent;
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("[{}] Message sent with id {}", boltId, msg.getMessageId());
                                }
                            }
                        }

                        return null;
                    });
                }
            }
        } catch (Exception e) {
            LOG.error("[{}] Message processing failed", boltId, e);
            collector.reportError(e);
            collector.fail(input);
        }
    }

    public void close() {
        try {
            LOG.info("[{}] Closing Pulsar producer on topic {}", boltId, this.topic);
            if (client != null) {
                client.close();
            }
        } catch (PulsarClientException e) {
            LOG.error("[{}] Error closing Pulsar producer on topic {}", boltId, this.topic, e);
        }
    }

    @Override
    public void cleanup() {
        close();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        this.tupleToMessageMapper.declareOutputFields(declarer);
    }

    protected static boolean isTickTuple(Tuple tuple) {
        return tuple != null && Constants.SYSTEM_COMPONENT_ID.equals(tuple.getSourceComponent())
                && Constants.SYSTEM_TICK_STREAM_ID.equals(tuple.getSourceStreamId());
    }

    public static ClientConfiguration getDefaultClientConfiguration() {
        return new ClientConfiguration();
    }

    public static ProducerConfiguration getDefaultProducerConfiguration() {
        return new ProducerConfiguration().
                setMaxPendingMessages(DEFAULT_MAX_PENDING_MESSAGES).
                setSendTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * Helpers for metrics
     */

    @SuppressWarnings({ "rawtypes" })
    ConcurrentMap getMetrics() {
        metricsMap.put(NO_OF_MESSAGES_SENT, messagesSent);
        metricsMap.put(PRODUCER_RATE, ((double) messagesSent) / this.metricsTimeIntervalSeconds);
        metricsMap.put(PRODUCER_THROUGHPUT_BYTES,
                ((double) messageSizeSent) / this.metricsTimeIntervalSeconds);
        return metricsMap;
    }

    void resetMetrics() {
        messagesSent = 0;
        messageSizeSent = 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getValueAndReset() {
        ConcurrentMap metrics = getMetrics();
        resetMetrics();
        return metrics;
    }

//    public static class Builder {
//        private String serviceUrl;
//        private String topic;
//        private ClientConfiguration clientConfiguration;
//        private ProducerConfiguration producerConfiguration;
//        private TupleToMessageMapper tupleToMessageMapper;
//        private int metricsTimeIntervalInSeconds;
//
//        @SuppressWarnings("HiddenField")
//        public PulsarBolt.Builder setServiceUrl(String serviceUrl) {
//            this.serviceUrl = serviceUrl;
//            return this;
//        }
//
//        @SuppressWarnings("HiddenField")
//        public PulsarBolt.Builder setTopic(String topic) {
//            this.topic = topic;
//            return this;
//        }
//
//        @SuppressWarnings("HiddenField")
//        public PulsarBolt.Builder setClientConfiguration(
//                ClientConfiguration clientConfiguration) {
//            this.clientConfiguration = clientConfiguration;
//            return this;
//        }
//
//        @SuppressWarnings("HiddenField")
//        public PulsarBolt.Builder setProducerConfiguration(
//                ProducerConfiguration producerConfiguration) {
//            this.producerConfiguration = producerConfiguration;
//            return this;
//        }
//
//        @SuppressWarnings("HiddenField")
//        public PulsarBolt.Builder setTupleToMessageMapper(
//                TupleToMessageMapper tupleToMessageMapper) {
//            this.tupleToMessageMapper = tupleToMessageMapper;
//            return this;
//        }
//
//        public PulsarBolt.Builder setMetricsTimeInterval(int seconds) {
//            metricsTimeIntervalInSeconds = seconds;
//            return this;
//        }
//
//        public PulsarBolt build() {
//            Utils.checkNotNull(serviceUrl, "A service url must be provided");
//            Utils.checkNotNull(topic, "A topic must be provided");
//            Utils.checkNotNull(tupleToMessageMapper,
//                    "A TupleToMessageMapper must be provided");
//            return new PulsarBolt(this);
//        }
//    }

}