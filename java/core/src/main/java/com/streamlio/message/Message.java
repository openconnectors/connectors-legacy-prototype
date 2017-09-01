package com.streamlio.message;


public interface Message {

    byte[] getData();

    MessageId getMessageId();
}