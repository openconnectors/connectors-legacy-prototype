package org.streamlio.connect;

public interface Task<T> {

    void start(final T ctx) throws Exception;
    void stop() throws Exception;

}
