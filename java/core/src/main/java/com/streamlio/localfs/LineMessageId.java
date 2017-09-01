package com.streamlio.localfs;

import com.google.common.primitives.Longs;
import com.streamlio.message.MessageId;

public class LineMessageId implements MessageId {

    private final long lineNumber;

    public LineMessageId(final long lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public byte[] toByteArray() {
        return Longs.toByteArray(lineNumber);
    }
}
