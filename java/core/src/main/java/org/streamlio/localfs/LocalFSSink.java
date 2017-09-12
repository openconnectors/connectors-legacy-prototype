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

package org.streamlio.localfs;

import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.util.SinkConnectorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class LocalFSSink extends SinkConnector<SinkConnectorContext,LineDataBaseMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(LocalFSSink.class);

    private BufferedWriter writer;
    private AtomicLong linesWritten;

    @Override
    public void open(Config config) throws Exception {
        writer = new BufferedWriter(new FileWriter(config.getString(ConfigKeys.OUTPUT_FILEPATH_KEY)));
        linesWritten = new AtomicLong(0);
    }

    @Override
    public void publish(Collection<LineDataBaseMessage> lines) throws Exception {
        try {
            for(LineDataBaseMessage message : lines){
                writer.write(new String(message.getData()));
                writer.write("\n");
                linesWritten.incrementAndGet();
            }
            writer.flush();

        } catch (IOException e) {
            LOG.error("Problem writing to file, " + linesWritten.get() + " lines written", e);
            throw e;
        }
    }

    @Override
    public void flush() throws Exception {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }
}
