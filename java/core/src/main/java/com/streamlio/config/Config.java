package com.streamlio.config;

import java.util.Set;

public interface Config {

    String getString(final String propertyName);
    Integer getInt(final String propertyName);
    Long getLong(final String propertyName);
    Double getDouble(final String propertyName);
    Object getObject(final String propertyName);
    Set<String> getPropertyKeys();

}
