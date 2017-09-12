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

package examples.pulsar;

import examples.localfs.LocalStreamCopyRunner;
import org.streamlio.config.Config;
import org.streamlio.config.ConfigProvider;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContextSinkLinked;
import org.streamlio.connectors.pulsar.PulsarSource;
import org.streamlio.context.CopyContext;
import org.streamlio.runner.LinkedBasicRunner;
import org.streamlio.stream.PrintStreamSink;

public class LocalPulsarStreamWriterRunner extends LinkedBasicRunner {

    public LocalPulsarStreamWriterRunner(SourceConnector source, SourceContextSinkLinked sourceContext) {
        super(source, sourceContext);
    }

    public static void main(String[] args) throws Exception {

        PrintStreamSink sink = new PrintStreamSink();

        LocalStreamCopyRunner runner = new LocalStreamCopyRunner(
                new PulsarSource(),
                new CopyContext(sink)
        );

        Config config = new ConfigProvider();
        runner.setup(config);
        runner.run();
    }
}
