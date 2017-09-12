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
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;
import org.streamlio.localfs.LineDataBaseMessage;
import org.streamlio.message.BaseMessage;
import org.streamlio.util.SourceConnectorContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class StdInputStreamSource extends SourceConnector<SourceConnectorContext,BaseMessage> {

    private InputStream stream;
    private InputStreamReader streamReader;
    private BufferedReader bufferedReader;
    private AtomicLong linesRead;

    @Override
    public Collection poll() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public void open(Config config) throws Exception {
        stream = System.in;
        streamReader = new InputStreamReader(stream);
        bufferedReader = new BufferedReader(streamReader);
        linesRead = new AtomicLong(0);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public void start(SourceContext<BaseMessage> ctx) throws Exception {

        while (true) {
            Thread.sleep(10);
            if (bufferedReader.ready()) {
                long id = linesRead.incrementAndGet();
                ctx.collect(Collections.singleton(
                        new LineDataBaseMessage(id, bufferedReader.readLine())));
            }
        }

    }

    @Override
    public void stop() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public String getVersion() {
        return StdStreamConVer.getVersion();
    }
}
