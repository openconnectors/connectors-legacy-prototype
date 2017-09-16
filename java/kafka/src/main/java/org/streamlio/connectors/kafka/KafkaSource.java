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

import org.apache.kafka.clients.consumer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streamlio.config.Config;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;
import org.streamlio.util.SourceConnectorContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class KafkaSource extends SourceConnector<SourceConnectorContext,String> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSource.class);
    private Consumer<String, String> consumer;
    private Properties props = new Properties();
    private String topic;
    private Boolean autoCommitEnabled;

    @Override
    public Collection<String> poll() throws Exception {
        return null;
    }

    @Override
    public void open(Config config) throws Exception {

        topic = config.getString("kafka.source.topic");
        autoCommitEnabled = config.getBoolean("kafka.source.auto_commit_enabled");

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString(ConfigKeys.KAFKA_SOURCE_BOOTSTRAP_SERVERS));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getString(ConfigKeys.KAFKA_SOURCE_GROUP_ID));
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, config.getString(ConfigKeys.KAFKA_SOURCE_FETCH_MIN_BYTES));
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.getString(ConfigKeys.KAFKA_SOURCE_AUTO_COMMIT_INTERVAL_MS));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getString(ConfigKeys.KAFKA_SOURCE_SESSION_TIMEOUT_MS));

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

    }

    @Override
    public void close() throws IOException {
        logger.info("Stopping kafka source");
        if(consumer != null) {
            consumer.close();
        }
        logger.info("Kafka source stopped.");
    }

    @Override
    public void start(SourceContext<String> ctx) throws Exception {

        logger.info("Starting kafka source");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        logger.info("Kafka source started.");
        ConsumerRecords<String, String> records;
        while(true){
            records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                logger.debug("Message received from kafka, key: {}. value: {}", record.key(), record.value());
                ctx.collect(Collections.singleton(record.value()));
            }
            if (!autoCommitEnabled) {
                consumer.commitSync();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        logger.info("Stopping kafka source");
        if(consumer != null) {
            consumer.close();
        }
        logger.info("Kafka source stopped.");
    }

    @Override
    public String getVersion() {
        return KafkaConnectorVersion.getVersion();
    }
}
