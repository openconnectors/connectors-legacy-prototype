package com.streamlio.task;

import com.streamlio.config.Config;
import com.streamlio.message.SinkMessage;

import java.util.Collection;

/**
 *
 */
public abstract class SinkTask implements Task{

    public abstract void publish(final Collection<SinkMessage> messages);

    @Override
    public void start(Config config) {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getVersion() {
        return null;
    }

}
