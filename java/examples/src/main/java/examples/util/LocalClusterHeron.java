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

import backtype.storm.utils.ConfigUtils;
import com.twitter.heron.api.HeronTopology;

import com.twitter.heron.simulator.Simulator;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.NotAliveException;

import java.util.Map;

public class LocalClusterHeron {

    private final Simulator simulator;
    private String topologyName;
    private Map conf;
    private HeronTopology topology;

    public LocalClusterHeron() {
        this.simulator = new Simulator();
        resetFields();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void submitTopology(String topoName,
                               Map config,
                               HeronTopology heronTopology) throws Exception {
        assertNotAlive();

        this.topologyName = topoName;
        this.conf = config;
        this.topology = heronTopology;

        simulator.submitTopology(topoName,
                ConfigUtils.translateConfig(config),
                heronTopology);
    }

    public void killTopology(String topoName) throws NotAliveException {
        assertAlive(topoName);
        simulator.killTopology(topoName);
        resetFields();
    }

    public void activate(String topoName) throws NotAliveException {
        assertAlive(topoName);
        simulator.activate(topoName);
    }

    public void deactivate(String topoName) throws NotAliveException {
        assertAlive(topoName);
        simulator.deactivate(topoName);
    }

    public void shutdown() {
        resetFields();
        simulator.shutdown();
    }

    public String getTopologyConf(String topologyName) {
        try {
            assertAlive(topologyName);
            return this.topologyName;
        } catch (NotAliveException ex) {
            return null;
        }
    }

    public HeronTopology getTopology(String topologyName) {
        try {
            assertAlive(topologyName);
            return this.topology;
        } catch (NotAliveException ex) {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public Map getState() {
        throw new RuntimeException("Heron does not support LocalCluster state yet...");
    }

    private void resetFields() {
        this.topologyName = null;
        this.topology = null;
        this.conf = null;
    }

    private void assertAlive(String topoName) throws NotAliveException {
        if (this.topologyName == null || !this.topologyName.equals(topoName)) {
            throw new NotAliveException();
        }
    }

    private void assertNotAlive() throws Exception {
        // only one topology is allowed to run. A topology is running if the topologyName is set.
        if (this.topologyName != null) {
            throw new Exception();
        }
    }
}
