package com.streamlio.api.source;


public interface Message {

    byte[] getData();

    MessageId getMessageId();
}