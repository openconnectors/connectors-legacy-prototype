package com.streamlio.connect;

import java.io.Closeable;
import java.util.Collection;

public interface SourceContext<W> extends Closeable {

    void collect(Collection<W> element);

}
