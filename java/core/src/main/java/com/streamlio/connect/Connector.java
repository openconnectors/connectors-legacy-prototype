/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, LocalFSConVer 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.streamlio.connect;

import com.streamlio.config.Config;
import com.streamlio.util.TaskConfig;
import com.streamlio.util.Versionable;

import java.io.Closeable;

public abstract class Connector<T extends TaskConfig, U extends ConnectorContext, V extends Config>
        implements Versionable, Closeable{

    private T taskConfig;
    private U context;
    private int taskParallelism;

    public void initialize(U ctx){
        this.context = ctx;
        this.taskConfig = null;
        this.taskParallelism = 1;
    }

    public void initialize(U ctx, T taskConfig){
        this.context = ctx;
        this.taskConfig = taskConfig;
        this.taskParallelism = 1;
    }

    public void initialize(U ctx, T taskConfig, int taskParallelism){
        this.context = ctx;
        this.taskConfig = taskConfig;
        this.taskParallelism = taskParallelism;
    }

    public abstract void open(V config) throws Exception;

    public void reset(V config) throws Exception{
        close();
        open(config);
    }

    public T getTaskConfig() {
        return taskConfig;
    }

    public U getContext() {
        return context;
    }
}