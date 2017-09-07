package examples;

import com.streamlio.config.PropertiesConfig;
import com.streamlio.localfs.LineBolt;
import com.streamlio.localfs.LineSpout;
import com.twitter.heron.api.Config;
import com.twitter.heron.api.topology.TopologyBuilder;
import com.twitter.heron.common.basics.ByteAmount;
import examples.util.HelperRunner;

public class FileCopyTopology {

    private FileCopyTopology() { }

    public static void main(String[] args) throws Exception {

        args = new String[]{"FileCopy", "Simulator"};

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new LineSpout());

        builder.setBolt("sink", new LineBolt()).globalGrouping("spout");

        PropertiesConfig config = new PropertiesConfig();

        Config conf = new Config();

        for(String s : config.getPropertyKeys()){
            conf.put(s, config.getObject(s));
        }

        com.twitter.heron.api.Config.setComponentRam(conf, "spout", ByteAmount.fromMegabytes(256));
        com.twitter.heron.api.Config.setComponentRam(conf, "sink", ByteAmount.fromMegabytes(256));

        //submit the topology
        HelperRunner.runTopology(args, builder.createTopology(), conf);

    }

}