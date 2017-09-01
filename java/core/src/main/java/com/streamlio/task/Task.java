package com.streamlio.task;

import com.streamlio.util.Versionable;
import com.streamlio.config.Config;

public interface Task extends Versionable{

    void start(final Config config);
    void stop();

}
