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

package examples.util;

import com.twitter.heron.api.Config;
import com.twitter.heron.api.HeronSubmitter;
import com.twitter.heron.api.HeronTopology;

public class HelperRunner {

    private static final String MODE_OF_OPERATION_CLUSTER = "Cluster";
    private static final String MODE_OF_OPERATION_SIMULATOR = "Simulator";
    private static final int NUMBER_OF_WORKERS = 3;  //default value
    private static final int DEFAULT_RUNTIME_IN_SECONDS = 60;

    private static final long MILLIS_IN_SEC = 1000;

    private HelperRunner() {}

    public static void runTopology(String[] args,
                                   HeronTopology topology,
                                   Config conf) throws Exception {

        if (args != null) {
            if (args.length < 2) {
                Exception exception = new IllegalArgumentException("Illegal number of command line arguments supplied." +
                        "\nPlease provide the topologyName as the first argument and either " +
                        "'Cluster' or 'Simulator' as the second argument.");
                throw exception;
            }

            if (!args[1].equals(MODE_OF_OPERATION_CLUSTER) && !args[1].equals(MODE_OF_OPERATION_SIMULATOR)) {
                Exception exception = new IllegalArgumentException("The allowed values for the second argument is either" +
                        " 'Cluster' or 'Simulator'. Please provide a valid value for the second argument.");
                throw exception;
            }

            String topologyName = args[0];

            if (args[1].equals(MODE_OF_OPERATION_CLUSTER)) {
                HelperRunner.runTopologyRemotely(topology, topologyName, conf);
            } else {
                conf.setComponentParallelism(NUMBER_OF_WORKERS);
                HelperRunner.runTopologyLocally(topology, topologyName, conf, DEFAULT_RUNTIME_IN_SECONDS);
            }

        }

    }

    private static void runTopologyRemotely(HeronTopology topology, String topologyName, Config conf)
            throws Exception {
        HeronSubmitter.submitTopology(topologyName, conf, topology);
    }

    private static void runTopologyLocally(HeronTopology topology,
                                           String topologyName,
                                           Config conf,
                                           int runtimeInSeconds)
            throws Exception {
        LocalClusterHeron cluster = new LocalClusterHeron();
        cluster.submitTopology(topologyName, conf, topology);
        Thread.sleep((long) runtimeInSeconds * MILLIS_IN_SEC);
        cluster.killTopology(topologyName);
        cluster.shutdown();

    }
}