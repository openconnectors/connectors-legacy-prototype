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
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;
import org.streamlio.util.SourceConnectorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

public class LocalFSSource extends SourceConnector<SourceConnectorContext,LineDataBaseMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(LocalFSSource.class);

    private BufferedReader reader;
    private AtomicLong linesRead;

    @Override
    public void open(Config config) throws Exception {
        reader = new BufferedReader(new FileReader(
                config.getString(ConfigKeys.INPUT_FILEPATH_KEY)));
        linesRead = new AtomicLong(0);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public String getVersion() {
        return LocalFSConVer.getVersion();
    }


    @Override
    public void start(SourceContext<LineDataBaseMessage> ctx) throws Exception {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                long id = linesRead.incrementAndGet();
                ctx.collect(Collections.singletonList(new LineDataBaseMessage(id, line)));
            }
            LOG.info("Finished reading file, " + linesRead.get() + " lines read");
            this.close();
            ctx.close();
        } catch (Exception e) {
            LOG.error("Problem reading file, " + linesRead.get() + " lines read", e);
            throw e;
        }
    }

    @Override
    public void stop() throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public Collection<LineDataBaseMessage> poll() throws Exception {
        throw new NotImplementedException();
    }

}
