# copyright 2016 twitter. all rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
'''Example WordCountPulsarTopology'''
import sys

from pyheron import Grouping, TopologyBuilder, constants
from heron.examples.src.python.spouts.nameage_pulsarspout import NameAgePulsarSpout
from heron.examples.src.python.bolts.count_bolt import CountBolt
from pulsar_spout import PulsarSpout

# Topology is defined using a topology builder
# Refer to multi_stream_topology for defining a topology by subclassing Topology
if __name__ == '__main__':
  if len(sys.argv) != 2:
    print "Topology's name is not specified"
    sys.exit(1)

  builder = TopologyBuilder(name=sys.argv[1])

  word_spout = builder.add_spout("pulsar_word_spout", NameAgePulsarSpout, par=2,
                                 optional_outputs=NameAgePulsarSpout.nameagefields,
                                 config={PulsarSpout.serviceUrl: "pulsar://localhost:6650",
                                         PulsarSpout.topicName:
                                         "persistent://sample/standalone/ns1/my-topic"})
  count_bolt = builder.add_bolt("count_bolt", CountBolt, par=2,
                                inputs={word_spout: Grouping.fields('Name')},
                                config={constants.TOPOLOGY_TICK_TUPLE_FREQ_SECS: 10})

  topology_config = {constants.TOPOLOGY_ENABLE_ACKING: True}
  builder.set_config(topology_config)

  builder.build_and_submit()
