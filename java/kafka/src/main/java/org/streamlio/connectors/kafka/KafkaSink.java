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

package org.streamlio.connectors.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.util.SinkConnectorContext;
import org.streamlio.util.Utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class KafkaSink extends SinkConnector<SinkConnectorContext,String> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSink.class);
    private Producer<String, String> producer;
    private Properties props = new Properties();
    private String topic;

    @Override
    public void publish(Collection<String> messages) throws Exception {
        ProducerRecord<String, String> record;
        for(String message : messages){
            record = new ProducerRecord<String, String>(topic, message);
            logger.debug("Message sending to kafka, record={}.", record);
            producer.send(record);
        }
    }

    @Override
    public void flush() throws Exception {
        producer.flush();
    }

    @Override
    public void open(Config config) throws Exception {

        topic = config.getString(ConfigKeys.KAFKA_SINK_TOPIC);

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString(ConfigKeys.KAFKA_SINK_BOOTSTRAP_SERVERS));
        props.put(ProducerConfig.ACKS_CONFIG, config.getString(ConfigKeys.KAFKA_SINK_ACKS));
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, config.getString(ConfigKeys.KAFKA_SINK_BATCH_SIZE));
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, config.getString(ConfigKeys.KAFKA_SINK_MAX_REQUEST_SIZE));

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);

        logger.info("Kafka sink started.");

    }

    @Override
    public void close() throws IOException {
        producer.close();
        logger.info("Kafka sink stoped.");
    }

    @Override
    public String getVersion() {
        return KafkaConnectorVersion.getVersion();
    }
}
