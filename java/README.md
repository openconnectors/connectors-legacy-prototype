#Connectors Framework

This project is to develop the framework and the there underlying implementations for input output to various external storage and data processing systems.

## Design

The basic design involves around a high level abstract connector component.
Which is specialized into two different types Source and Sink Connectors.

Source represents something that provides and input stream and is generally the initiation of pipeline where as Sink will provide a terminal context generally ending a pipeline .


## Lifecycle

For a Source Connector the process starts with a initialization phase , 




## Configuration

For various components the Config context is available in most methods with interfaces a key value type lookup the preferred implementation is the PropertiesConfig class , which uses a two phase bootstrap . First loading base properties from "connector.properties" and then overriding and extending the keyset with "local.connector.properties" being looked at from the class path.


## Execution Context / Local Runner





## Heron Adapters


## Specialized options

In dealing with more complex and parallel execution environments and dealing with distributed data stores the following actions might have to be modeled with the 


### Partition management

### Checkpoint

### Acknowledgement

### Throttling






## Copy and ETL Context

## List of connectors Status

Framework :

Early Alpha

Core Connectors:

Pulsar, Kafka and apache file io


Additional :

JDBC, Redis, Elastic Search





 




