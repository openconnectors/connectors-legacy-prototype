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
from collections import Counter

from pyheron import Grouping, TopologyBuilder, constants, Bolt
from pulsar_spout import PulsarSpout

# Count Bolt defn
# pylint: disable=unused-argument
class CountBolt(Bolt):
  """CountBolt"""
  # output field declarer
  #outputs = ['word', 'count']

  def initialize(self, config, context):
    self.logger.info("In prepare() of CountBolt")
    self.counter = Counter()
    self.total = 0

    self.logger.info("Component-specific config: \n%s" % str(config))
    self.logger.info("Context: \n%s" % str(context))

  def _increment(self, word, inc_by):
    self.counter[word] += inc_by
    self.total += inc_by

  def process(self, tup):
    word = tup.values[0]
    self._increment(word, 1)
    self.ack(tup)

  def process_tick(self, tup):
    self.log("Got tick tuple!")
    self.log("Current map: %s" % str(self.counter))

# Topology is defined using a topology builder
# Refer to multi_stream_topology for defining a topology by subclassing Topology
if __name__ == '__main__':
  if len(sys.argv) != 2:
    print "Topology's name is not specified"
    sys.exit(1)

  builder = TopologyBuilder(name=sys.argv[1])

  word_spout = builder.add_spout("pulsar_word_spout", PulsarSpout, par=2,
                                 config={PulsarSpout.serviceUrl: "pulsar://localhost:6650",
                                         PulsarSpout.topicName:
                                         "persistent://sample/standalone/ns1/my-topic"})
  count_bolt = builder.add_bolt("count_bolt", CountBolt, par=2,
                                inputs={word_spout: Grouping.fields('payload')},
                                config={constants.TOPOLOGY_TICK_TUPLE_FREQ_SECS: 10})

  topology_config = {constants.TOPOLOGY_ENABLE_ACKING: True}
  builder.set_config(topology_config)

  builder.build_and_submit()
