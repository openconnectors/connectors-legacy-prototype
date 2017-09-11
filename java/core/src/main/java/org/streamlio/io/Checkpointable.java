package org.streamlio.io;

public interface Checkpointable {

    void checkppoint();

    void restore();

}
