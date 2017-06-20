"""module for pulsar spout: PulsarSpout"""

import pulsar

from pyheron import Spout, constants

class PulsarSpout(Spout):
  """PulsarSpout: reads from a pulsar topic"""

  # pylint: disable=too-many-instance-attributes

  # output field declarer
  # fixme(skukarni):- Add sophisticated Schema equivalent like Kafka
  outputs = ['payload']

  # TopologyBuilder uses these constants to set
  # cluster/topicname
  serviceUrl = "PULSAR_SERVICE_URL"
  topicName = "PULSAR_TOPIC"
  receiveTimeoutMs = "PULSAR_RECEIVE_TIMEOUT_MS"

  def initialize(self, config, context):
    """Implements Pulsar Spout's initialize method"""
    self.logger.info("In initialize() of PulsarSpout")

    self.emit_count = 0
    self.ack_count = 0
    self.fail_count = 0

    if not PulsarSpout.serviceUrl in config or not PulsarSpout.topicName in config:
      self.logger.fatal("Need to specify both serviceUrl and topicName")
    self.pulsar_cluster = config[PulsarSpout.serviceUrl]
    self.topic = config[PulsarSpout.topicName]
    self.acking = config[constants.TOPOLOGY_ENABLE_ACKING]
    if self.acking:
      self.acking_timeout = 1000 * int(config[constants.TOPOLOGY_MESSAGE_TIMEOUT_SECS])
    else:
      self.acking_timeout = 30000
    if PulsarSpout.receiveTimeoutMs in config:
      self.receive_timeout_ms = config[PulsarSpout.receiveTimeoutMs]
    else:
      self.receive_timeout_ms = 10

    # We currently use the high level consumer api
    # For supporting exactly once, we will need to switch
    # to using lower level Reader api, when it becomes
    # available in python
    self.client = pulsar.Client(self.pulsar_cluster)
    try:
      self.consumer = self.client.subscribe(self.topic, context.topology_name(),
                                            consumer_type=pulsar.ConsumerType.Failover,
                                            unacked_messages_timeout_ms=self.acking_timeout)
    except Exception as e:
      self.logger.fatal("Pulsar client subscription failed: %s" % str(e))

    self.logger.info("Component-specific config: \n%s" % str(config))
    self.logger.info("Context: \n%s" % str(context))

  def next_tuple(self):
    try:
      msg = self.consumer.receive(timeout_millis=self.receive_timeout_ms)
      self.emit([msg.data()], tup_id=msg.message_id())
      self.emit_count += 1
    except Exception as e:
      self.logger.debug("Exception during recieve: %s" % str(e))

  def ack(self, tup_id):
    self.ack_count += 1
    self.consumer.acknowledge(tup_id)

  def fail(self, tup_id):
    self.fail_count += 1
    self.logger.debug("Failed tuple %s" % str(tup_id))
