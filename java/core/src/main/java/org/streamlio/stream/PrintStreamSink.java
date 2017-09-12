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

package org.streamlio.stream;

import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.util.SinkConnectorContext;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class PrintStreamSink extends SinkConnector<SinkConnectorContext,String> {

    private String outputFormat;
    private AtomicLong linesReceived;
    private PrintStream stream = System.out;

    @Override
    public void publish(Collection<String> messages) throws Exception {
        for(String message : messages){
            long id = linesReceived.incrementAndGet();
            final String output = String.format(
                    outputFormat,
                    id,
                    message);
            stream.println(output);
        }
    }

    @Override
    public void flush() throws Exception {
        stream.flush();
    }

    @Override
    public void open(Config config) throws Exception {
        outputFormat = config.getString(ConfigKeys.OUTPUT_FORMAT_KEY, ConfigKeys.DEFAULT_OUTPUT_FORMAT);
        stream = System.out;
        linesReceived = new AtomicLong(0);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public String getVersion() {
        return StdStreamConVer.getVersion();
    }
}
