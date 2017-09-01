package com.streamlio.source;

import com.streamlio.connector.Connector;
import com.streamlio.io.ReaderContext;
import com.streamlio.io.ReadResult;

public interface SourceConnector<T extends ReadResult, U extends ReaderContext, V> extends Connector {

}
