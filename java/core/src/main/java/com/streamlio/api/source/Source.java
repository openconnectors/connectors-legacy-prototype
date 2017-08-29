package com.streamlio.api.source;

import com.streamlio.io.ReaderContext;
import com.streamlio.io.ReadIO;
import com.streamlio.io.ReadResult;
import com.streamlio.io.Readable;

public interface Source {

    Readable open(final ReadIO context);

}
