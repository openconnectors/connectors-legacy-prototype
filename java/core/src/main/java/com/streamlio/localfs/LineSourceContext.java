package com.streamlio.localfs;

import com.streamlio.connect.SourceContext;
import com.streamlio.message.Message;

import java.io.IOException;
import java.util.Collection;

public class LineSourceContext implements SourceContext<Message>{


    @Override
    public void collect(Collection<Message> element) {

    }

    @Override
    public void close() throws IOException {

    }
}
