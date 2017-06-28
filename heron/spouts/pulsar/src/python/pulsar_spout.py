"""module for pulsar spout: PulsarSpout"""

import os
import tempfile

import pulsar

from heronpy import Spout, api_constants

def GenerateLogConfContents(logFileName):
  return """
# Define the root logger with appender file
log4j.rootLogger = INFO, FILE
# Define the file appender
log4j.appender.FILE=org.apache.log4j.DailyRollingFileAppender
log4j.appender.FILE.File=%s""" % logFileName + """
log4j.appender.FILE.Threshold=INFO
log4j.appender.FILE.DatePattern='.' yyyy-MM-dd-a
log4j.appender.FILE.layout=org.apache.log4j.PatternLayout
log4j.appender.FILE.layout.ConversionPattern=%d{yy-MM-dd HH:mm:ss.SSS} %X{pname}:%X{pid} %-5p %l- %m%n
"""

def GenerateLogConfig(context):
  namePrefix = str(context.get_component_id()) + "-" + str(context.get_task_id())
  logFileName = os.getcwd() + "/" + namePrefix
  flHandler = tempfile.NamedTemporaryFile(prefix=namePrefix, suffix='.conf',
                                          dir=os.getcwd(), delete=False)
  flHandler.write(GenerateLogConfContents(logFileName))
  flHandler.flush()
  flHandler.close()
  return flHandler.name

class PulsarSpout(Spout):
  """PulsarSpout: reads from a pulsar topic"""

  # pylint: disable=too-many-instance-attributes

  # pylint: disable=no-self-use
  def deserialize(self, msg):
    self.logger.info("Came in Base class deserialize")
    return [str(msg)]

  # TopologyBuilder uses these constants to set
  # cluster/topicname
  serviceUrl = "PULSAR_SERVICE_URL"
  topicName = "PULSAR_TOPIC"
  receiveTimeoutMs = "PULSAR_RECEIVE_TIMEOUT_MS"

  def initialize(self, config, context):
    """Implements Pulsar Spout's initialize method"""
    self.logger.info("Initializing PulsarSpout with the following")
    self.logger.info("Component-specific config: \n%s" % str(config))
    self.logger.info("Context: \n%s" % str(context))

    self.emit_count = 0
    self.ack_count = 0
    self.fail_count = 0

    if not PulsarSpout.serviceUrl in config or not PulsarSpout.topicName in config:
      self.logger.fatal("Need to specify both serviceUrl and topicName")
    self.pulsar_cluster = str(config[PulsarSpout.serviceUrl])
    self.topic = str(config[PulsarSpout.topicName])
    self.acking = config[api_constants.TOPOLOGY_ENABLE_ACKING]
    if self.acking:
      self.acking_timeout = 1000 * int(config[api_constants.TOPOLOGY_MESSAGE_TIMEOUT_SECS])
    else:
      self.acking_timeout = 30000
    if PulsarSpout.receiveTimeoutMs in config:
      self.receive_timeout_ms = config[PulsarSpout.receiveTimeoutMs]
    else:
      self.receive_timeout_ms = 10

    # First generate the config
    self.logConfFileName = GenerateLogConfig(context)
    self.logger.info("Generated LogConf at %s" % self.logConfFileName)

    # We currently use the high level consumer api
    # For supporting exactly once, we will need to switch
    # to using lower level Reader api, when it becomes
    # available in python
    self.client = pulsar.Client(self.pulsar_cluster, log_conf_file_path=self.logConfFileName)
    self.logger.info("Setup Client with cluster %s" % self.pulsar_cluster)
    try:
      self.consumer = self.client.subscribe(self.topic, context.get_topology_name(),
                                            consumer_type=pulsar.ConsumerType.Failover,
                                            unacked_messages_timeout_ms=self.acking_timeout)
    except Exception as e:
      self.logger.fatal("Pulsar client subscription failed: %s" % str(e))

    self.logger.info("Subscribed to topic %s" % self.topic)

  def next_tuple(self):
    try:
      msg = self.consumer.receive(timeout_millis=self.receive_timeout_ms)
    except Exception as e:
      self.logger.debug("Exception during recieve: %s" % str(e))
      return

    try:
      self.emit(self.deserialize(msg.data()), tup_id=msg.message_id())
      self.emit_count += 1
    except Exception as e:
      self.logger.info("Exception during emit: %s" % str(e))

  def ack(self, tup_id):
    self.ack_count += 1
    self.consumer.acknowledge(tup_id)

  def fail(self, tup_id):
    self.fail_count += 1
    self.logger.debug("Failed tuple %s" % str(tup_id))
