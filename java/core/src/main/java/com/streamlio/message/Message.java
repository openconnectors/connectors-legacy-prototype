package com.streamlio.message;

import java.io.Serializable;

public interface Message<T extends MessageId> extends Serializable {

    byte[] getData();

    T getMessageId();
}