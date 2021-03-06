/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
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
package org.streamlio.connectors.pulsar.connect.runtime;

import org.streamlio.common.ConnectorExecutionException;
import org.streamlio.common.util.PropertiesValidator;
import org.streamlio.connect.SinkConnector;
import org.streamlio.connectors.pulsar.connect.config.PulsarConnectorConfiguration;

import java.util.Properties;

public abstract class PulsarConnectorRunner {

    public static PulsarConnectorRunner fromProperties(Properties properties) {
        PropertiesValidator.validateThrowIfMissingKeys(properties,
                PulsarConnectorConfiguration.KEY_TOPIC,
                PulsarConnectorConfiguration.KEY_CONNECTOR);

        try {
            final String connectorClass = properties.getProperty(PulsarConnectorConfiguration.KEY_CONNECTOR);
            if (SinkConnector.class.isAssignableFrom(Class.forName(connectorClass))) {
                return SinkConnectorRunner.fromProperties(properties);
            }

            throw new RuntimeException("unknown connector class [ " + connectorClass + "]");
        } catch (ClassNotFoundException cnfe) {
            throw new ConnectorExecutionException(cnfe);
        }
    }

    public abstract void run();
}
