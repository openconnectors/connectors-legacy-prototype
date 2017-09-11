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
        return config.getInt(propertyName);
    }

    @Override
    public Long getLong(String propertyName) {
        return config.getLong(propertyName);
    }

    @Override
    public Double getDouble(String propertyName) {
        return config.getDouble(propertyName);
    }

    @Override
    public Boolean getBoolean(String propertyName) {
        return config.getBoolean(propertyName);
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
        return config.hasPath(propertyName) ? this.getInt(propertyName) : defaultValue;
    }

    @Override
    public Long getLong(String propertyName, Long defaultValue) {
        return config.hasPath(propertyName) ? this.getLong(propertyName) : defaultValue;
    }

    @Override
    public Double getDouble(String propertyName, Double defaultValue) {
        return config.hasPath(propertyName) ? this.getDouble(propertyName) : defaultValue;
    }

    @Override
    public Boolean getBoolean(String propertyName, Boolean defaultValue) {
        return config.hasPath(propertyName) ? this.getBoolean(propertyName) : defaultValue;
    }


    @Override
    public Object getObject(String propertyName, Object defaultValue) {
        return config.hasPath(propertyName) ? this.getObject(propertyName) : defaultValue;
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
