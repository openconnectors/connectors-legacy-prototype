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

package org.streamlio.runner;
import org.streamlio.config.Config;
import org.streamlio.connect.SinkConnector;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;

public abstract class BasicRunner
        <T extends SourceConnector, U extends SourceContext, V extends SinkConnector> {

    T source;
    U sourceContext;
    V sink;

    public BasicRunner(T source, U sourceContext, V sink){
        this.source = source;
        this.sourceContext = sourceContext;
        this.sink = sink;
    }

    public void setup(Config config) throws Exception{
        this.source.open(config);
        this.sink.open(config);
        this.sourceContext.setup(config);
    }

    public void run() throws Exception {
        this.source.start(this.sourceContext);
    }

}
