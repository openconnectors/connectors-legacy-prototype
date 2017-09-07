package examples;

import examples.util.HelperRunner;
import com.streamlio.localfs.LineBolt;
import com.streamlio.localfs.LineSpout;
import com.twitter.heron.api.Config;
import com.twitter.heron.api.topology.TopologyBuilder;
import com.twitter.heron.common.basics.ByteAmount;

public class FileCopyTopology {

    private FileCopyTopology() { }

    public static void main(String[] args) throws Exception {

        args = new String[]{"FileCopy", "Simulator"};

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new LineSpout());

        builder.setBolt("sink", new LineBolt());

        Config conf = new Config();

        com.twitter.heron.api.Config.setComponentRam(conf, "spout", ByteAmount.fromMegabytes(256));
        com.twitter.heron.api.Config.setComponentRam(conf, "sink", ByteAmount.fromMegabytes(256));

        //submit the topology
        HelperRunner.runTopology(args, builder.createTopology(), conf);

    }

}