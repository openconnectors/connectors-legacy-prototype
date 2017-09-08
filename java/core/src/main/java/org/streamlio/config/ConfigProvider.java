package org.streamlio.config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigProvider implements Config {

    private final com.typesafe.config.Config config;

    public ConfigProvider(){
        this.config = ConfigFactory.load();
    }

    @Override
    public String getString(String propertyName) {
        return config.getString(propertyName);
    }

    @Override
    public Integer getInt(String propertyName) {
        return null;
    }

    @Override
    public Long getLong(String propertyName) {
        return null;
    }

    @Override
    public Double getDouble(String propertyName) {
        return null;
    }

    @Override
    public Boolean getBoolean(String propertyName) {
        return null;
    }

    @Override
    public Character getCharacter(String propertyName) {
        return null;
    }

    @Override
    public Byte getByte(String propertyName) {
        return null;
    }

    @Override
    public Object getObject(String propertyName) {
        return config.getAnyRef(propertyName);
    }

    @Override
    public String getString(String propertyName, String defaultValue) {
        return config.hasPath(propertyName) ? this.getString(propertyName) : defaultValue;
    }

    @Override
    public Integer getInt(String propertyName, Integer defaultValue) {
        return null;
    }

    @Override
    public Long getLong(String propertyName, Long defaultValue) {
        return null;
    }

    @Override
    public Double getDouble(String propertyName, Double defaultValue) {
        return null;
    }

    @Override
    public Boolean getBoolean(String propertyName, Boolean defaultValue) {
        return null;
    }

    @Override
    public Character getCharacter(String propertyName, Character defaultValue) {
        return null;
    }

    @Override
    public Byte getByte(String propertyName, Byte defaultValue) {
        return null;
    }

    @Override
    public Object getObject(String propertyName, Object defaultValue) {
        return null;
    }

    @Override
    public Set<String> getPropertyKeys() {
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String,ConfigValue> entry : config.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }
}
