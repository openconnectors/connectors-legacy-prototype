
# Connectors Framework

This project is to develop the framework and the there underlying implementations for input and output to various external storage and data processing systems.

## Design

The basic design involves around a high level abstract connector component.
Which is specialized into two different types Source and Sink Connectors.

Source represents something that provides and input stream and is generally the initiation of pipeline where as Sink will provide a terminal context generally ending a pipeline .


## Lifecycle

For a Source Connector the process starts with a initialization phase, the start method is called post setup and is called with a 
"SourceContext" the context encompasses a collect method which should be called every time the sink is ready with new data. 
A poll method is also part of the interface or alternative or explicit data requests.


For a Sink Connector the process also with a initialization phase, it does not have a live task context , it in it's current form is not suppose to pull data but rather be given data to process via calling the publish data.


## Configuration

For various components the Config context is available in most methods with interfaces a key value type lookup the preferred implementation is the PropertiesConfig class , which uses a two phase bootstrap . First loading base properties from "connector.properties" and then overriding and extending the keyset with "local.connector.properties" being looked at from the class path.


## Execution instance / Local Runner

At the most basic level a pipeline needs to be composed of a source a context handled and a sink , to possible contexts are a copy and a etl context ie : "CopyContext" and "ETLContext"

Copy context simply transmits data from a source to sink , where an etl context does the same but adds a mapping phase between the source and sink which is chained link of transformation operations.


## Heron Adapters


## Specialized options

In dealing with more complex and parallel execution environments and dealing with distributed data stores the following actions might have to be modeled with the 


### Partition management

### Checkpoint

### Acknowledgement

### Throttling



## Copy and ETL Context


[`CopyContext.java`](core/src/main/java/com/streamlio/context/CopyContext.java)

[`ETLContext.java`](core/src/main/java/com/streamlio/context/ETLContext.java)


## Build Instructions adding new connectors

### Gradle commands

We use gradle for build management

build targets are relative to subprojects.

```
gradle core:jar // build packaged jar
gradle core:createPom // build maven compatible pom.xml
gradle core:compileJava // compileSources
gradle core:compileTestJava // compileTest
gradle core:fatJar // construct uber Jar
gradle core:sourcesJar // package sources into jar
gradle core:javadocJar // build java doc jar
```

### Adding new connectors

Create a new folder

update settings.gradle to include the new gradle module
update build.gradle to manage dependencies.

jar construction template is already provided via the folder name context.


## Examples

Local Runner for file copy

[`LocalFSCopyRunner.java`](examples/src/main/java/examples/LocalFSCopyRunner.java)

Source and Sink in a java process linked in Copy Context.

[`FileCopyTopology.java`](examples/src/main/java/examples/FileCopyTopology.java)

Heron Topology wrapping Heron spout and bolt into a simple topology

[`LocalStreamCopyRunner.java`](examples/src/main/java/examples/LocalStreamCopyRunner.java)

Local Echo std in to std in a a copy context

```
java -cp examples/build/libs/examples-uber.jar "examples.localfs.LocalStreamCopyRunner"
java -cp examples/build/libs/examples-uber.jar "examples.localfs.LocalFSCopyRunner"
java -cp examples/build/libs/examples-uber.jar "examples.localfs.FileCopyTopology"
```


## List of connectors Status

Framework :

Early Alpha

Core Connectors:

Pulsar, Kafka and apache file io


Additional :

JDBC, Redis, Elastic Search





 




