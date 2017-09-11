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

import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;
import com.twitter.heron.api.tuple.Values;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageBuilder;

public class Config {

    private static String SERVICE_URL = "pulsar://localhost:6650";
    private static String INPUT_TOPIC = "persistent://sample/standalone/ns1/sentences";
    private static String OUPTUT_TOPIC = "persistent://sample/standalone/ns1/wordcount";
    private static String SUBSCRIPTION = "heron-spout";

    @SuppressWarnings("serial")
    TupleToMessageMapper wordCountMapper = new TupleToMessageMapper() {

        @Override
        public Message toMessage(Tuple tuple) {
            return MessageBuilder.create().setContent(
                    String.format(
                            "{ \"word\" : \"%s\" , \"count\" : %d }", tuple.getString(0), tuple.getInteger(1))
                            .getBytes())
                    //.setKey(tuple.getString(0))
                    .build();
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }

    };


    @SuppressWarnings("serial")
    MessageToValuesMapper sentenceMapper = new MessageToValuesMapper() {

        @Override
        public Values toValues(Message msg) {
            return new Values(new String(msg.getData()));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            // declare the output fields
            declarer.declare(new Fields("sentence"));
        }
    };

    PulsarSpout randomSentenceSpout = new PulsarSpout.Builder()
            .setServiceUrl(SERVICE_URL)
            .setTopic(INPUT_TOPIC)
            .setSubscription(SUBSCRIPTION)
            .setMessageToValuesMapper(sentenceMapper)
            .build();

    PulsarBolt messageBolt = new PulsarBolt.Builder()
            .setServiceUrl(SERVICE_URL)
            .setTopic(OUPTUT_TOPIC)
            .setTupleToMessageMapper(wordCountMapper)
            .build();
}
