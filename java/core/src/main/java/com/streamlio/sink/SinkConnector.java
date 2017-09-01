package com.streamlio.sink;

import com.streamlio.connector.Connector;
import com.streamlio.config.Config;
import com.streamlio.io.*;

public interface SinkConnector<T extends WriteResult, U extends WriterContext, V extends Config> extends Connector {

}
