package org.streamlio.config;

import java.util.Set;

public interface Config {

    String getString(final String propertyName);
    Integer getInt(final String propertyName);
    Long getLong(final String propertyName);
    Double getDouble(final String propertyName);
    Boolean getBoolean(final String propertyName);
    Object getObject(final String propertyName);

    String getString(final String propertyName, final String defaultValue);
    Integer getInt(final String propertyName, final Integer defaultValue);
    Long getLong(final String propertyName, final Long defaultValue);
    Double getDouble(final String propertyName, final Double defaultValue);
    Boolean getBoolean(final String propertyName, final Boolean defaultValue);
    Object getObject(final String propertyName, final Object defaultValue);

    Set<String> getPropertyKeys();

}