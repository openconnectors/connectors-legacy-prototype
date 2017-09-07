package examples;

import com.streamlio.config.PropertiesConfig;
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

        builder.setSpout("spout1", new LineSpout());

        builder.setBolt("sink1", new LineBolt()).globalGrouping("spout1");

        PropertiesConfig config = new PropertiesConfig();

        Config conf = new Config();

        for(String s : config.getPropertyKeys()){
            conf.put(s, config.getObject(s));
        }

        com.twitter.heron.api.Config.setComponentRam(conf, "spout1", ByteAmount.fromMegabytes(256));
        com.twitter.heron.api.Config.setComponentRam(conf, "sink1", ByteAmount.fromMegabytes(256));


        System.out.println(builder.createTopology().toString());

        //submit the topology
        HelperRunner.runTopology(args, builder.createTopology(), conf);

    }

}