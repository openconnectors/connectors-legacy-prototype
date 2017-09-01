package com.streamlio.task;

import com.streamlio.config.Config;
import com.streamlio.message.SourceMessage;

import java.util.Collection;

public abstract class SourceTask implements Task {

    Collection<SourceMessage> poll();

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
