package org.streamlio.message;

import java.io.Serializable;

public interface MessageId extends Serializable {

    byte[] toByteArray();

}
