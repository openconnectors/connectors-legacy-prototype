package com.streamlio.message;


public interface Message<T extends MessageId> {

    byte[] getData();

    T getMessageId();
}