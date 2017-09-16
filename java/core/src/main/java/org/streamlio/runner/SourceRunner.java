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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streamlio.connect.SourceConnector;
import org.streamlio.connect.SourceContext;

import java.util.concurrent.atomic.AtomicBoolean;

public class SourceRunner {

    private static final Logger logger = LoggerFactory.getLogger(SourceRunner.class);

    private static final long MAX_BACKOFF_SLEEP = 5000;

    private SourceConnector source;

    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    private Thread runnerThread;

    private SourceContext sourceContext;

    public SourceRunner(SourceConnector source, SourceContext sourceContext) {
        this.source = source;
    }

    public void start() throws Exception {
        source.start(sourceContext);
        // only PollingSourceConnector
//        if (source instanceof PollingSourceConnector) {
//            final PollingSourceConnector pollingSource = (PollingSourceConnector) source;
//            runnerThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    logger.info("Polling runner starting. Source={}", source.getName());
//                    while (!shouldStop.get()) {
//                        try {
//                            pollingSource.process();
//                        } catch (Exception e) {
//                            logger.error("Unhandled exception, logging and sleeping for " + MAX_BACKOFF_SLEEP + "ms", e);
//                            try {
//                                Thread.sleep(MAX_BACKOFF_SLEEP);
//                            } catch (InterruptedException ex) {
//                                Thread.currentThread().interrupt();
//                            }
//                        }
//                    }
//                }
//
//            });
        runnerThread.setName("SourceRunner");
        runnerThread.start();

    }

    public void stop() throws Exception {
        if (source instanceof PollingSourceConnector) {
            shouldStop.set(true);
            try {
                runnerThread.interrupt();
                runnerThread.join();
            } catch (InterruptedException e) {
                logger.warn("Interrupted while waiting for polling runner to stop.", e);
                Thread.currentThread().interrupt();
            }
        }
        source.stop();
    }

}