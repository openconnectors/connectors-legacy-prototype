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
import org.streamlio.connect.SinkConnector;

import java.util.concurrent.atomic.AtomicBoolean;

public class SinkRunner {

    private static final Logger logger = LoggerFactory.getLogger(SinkRunner.class);

    private static final long MAX_BACKOFF_SLEEP = 5000;

    private SinkConnector sink;

    private Thread runnerThread;

    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    public SinkRunner(SinkConnector sink) {
        this.sink = sink;
    }

    public void start() {
        //sink.start();

        runnerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Sink runner starting");
                while (!shouldStop.get()) {
//                    List<Processor> processors =  sink.getProcessors();
//                    for(Processor processor : processors) {
//                        try {
//                            ResultEvent event = processor.getResult();
//                            if(event != null) {
//                                sink.process(event);
//                            }
//                        } catch (Exception e) {
//                            logger.error("Unable to deliver event. Exception follows.", e);
//                            try {
//                                Thread.sleep(MAX_BACKOFF_SLEEP);
//                            } catch (InterruptedException ex) {
//                                Thread.currentThread().interrupt();
//                            }
//                        }
//                    }
                }
                logger.info("Sink runner exiting. ");
            }
        });
        runnerThread.setName("SinkRunner");
        runnerThread.start();
    }

    public void stop() {
        if (runnerThread != null) {
            shouldStop.set(true);
            runnerThread.interrupt();
            while (runnerThread.isAlive()) {
                try {
                    logger.info("Waiting for runner thread to exit");
                    runnerThread.join(500);
                } catch (InterruptedException e) {
                    logger.info("Interrupted while waiting for runner thread to exit. Exception follows.", e);
                }
            }
        }
    }

}