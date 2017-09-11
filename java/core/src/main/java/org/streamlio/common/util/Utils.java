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
package org.streamlio.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public final class Utils {
  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

  public static void closeSilently(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();

        // Suppress it since we ignore any exceptions
        // SUPPRESS CHECKSTYLE IllegalCatch
      } catch (Exception e) {
        LOG.warn("Failed to close {}", closeable, e);
      }
    }
  }

  public static <T> T checkNotNull(T reference, String errorMessage) {
    if (reference == null) {
      throw new NullPointerException(errorMessage);
    }

    return reference;
  }

  public static <T> T defaultIfNull(T reference, T defaultValue) {
    return reference != null ? reference : defaultValue;
  }

  private Utils() {
  }
}
