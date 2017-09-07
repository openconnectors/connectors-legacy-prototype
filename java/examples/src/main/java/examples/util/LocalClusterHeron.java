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
