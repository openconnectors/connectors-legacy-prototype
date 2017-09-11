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

package examples.localfs;

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