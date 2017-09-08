package org.streamlio.localfs;

import org.streamlio.message.Message;

public class LineDataMessage implements Message<LineMessageId> {

    private final String data;
    private final LineMessageId messageId;

    public LineDataMessage(long lineNum, String data) {
        this.data = data;
        this.messageId = new LineMessageId(lineNum);
    }

    @Override
    public byte[] getData() {
        return data.getBytes();
    }

    @Override
    public LineMessageId getMessageId() {
        return messageId;
    }

}
