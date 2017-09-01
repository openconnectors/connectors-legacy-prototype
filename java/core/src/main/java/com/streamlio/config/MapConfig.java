package com.streamlio.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MapConfig implements Config{

    final HashMap<String, Object> properties;

    public MapConfig(){
        properties = new HashMap<>();
    }

    public MapConfig(final HashMap<String, Object> props){
        properties = props;
    }

    @Override
    public String getString(String propertyName) {
        return properties.get(propertyName).toString();
    }

    @Override
    public Integer getInt(String propertyName) {
        return Integer.parseInt(properties.get(propertyName).toString());
    }

    @Override
    public Long getLong(String propertyName) {
        return Long.parseLong(properties.get(propertyName).toString());
    }

    @Override
    public Double getDouble(String propertyName) {
        return Double.parseDouble(properties.get(propertyName).toString());
    }

    @Override
    public Object getObject(String propertyName) {
        return properties.get(propertyName).toString();
    }

    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }
}
