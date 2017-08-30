/**
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
package com.streamlio.connectors.pulsar.connect.sink;

import com.streamlio.connectors.pulsar.connect.api.sink.SinkConnector;
import org.apache.pulsar.client.api.Message;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class PrintSinkConnector extends SinkConnector {

    private static final String OUTPUT_FORMAT = "[%s] %s";

    private PrintStream stream = System.out;

    @Override
    public void initialize(Properties properties) {
        stream = System.out;
    }

    @Override
    public void close() {
        stream = null;
    }

    @Override
    public void processMessage(Message message) throws IOException {
        final String output = String.format(OUTPUT_FORMAT,
                message.getMessageId().toString(),
                new String(message.getData()));
        stream.println(output);
    }

    @Override
    public void commit() throws Exception {
        // do nothing
    }
}