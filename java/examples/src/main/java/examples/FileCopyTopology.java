package examples;

import com.twitter.heron.api.Config;
import com.twitter.heron.api.topology.TopologyBuilder;
import com.twitter.heron.common.basics.ByteAmount;
import examples.util.HelperRunner;
import org.streamlio.config.ConfigProvider;
import org.streamlio.localfs.LineBolt;
import org.streamlio.localfs.LineSpout;

public class FileCopyTopology {

    private FileCopyTopology() { }

    public static void main(String[] args) throws Exception {

        args = new String[]{"FileCopy", "Simulator"};

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new LineSpout());

        builder.setBolt("sink", new LineBolt()).globalGrouping("spout");

        org.streamlio.config.Config config = new ConfigProvider();

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