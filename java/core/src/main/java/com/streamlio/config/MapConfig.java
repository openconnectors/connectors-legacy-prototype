package com.streamlio.config;

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
    public Boolean getBoolean(String propertyName) {
        return Boolean.parseBoolean(properties.get(propertyName).toString());
    }

    @Override
    public Character getCharacter(String propertyName) {
        final String temp = properties.get(propertyName).toString();
        if(temp != null && temp.length() > 0){
            return properties.get(propertyName).toString().charAt(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Byte getByte(String propertyName) {
        return Byte.parseByte(properties.get(propertyName).toString());
    }

    @Override
    public Object getObject(String propertyName) {
        return properties.get(propertyName).toString();
    }

    @Override
    public String getString(String propertyName, String defaultValue) {
        return (getString(propertyName) != null) ? getString(propertyName) : defaultValue;
    }

    @Override
    public Integer getInt(String propertyName, Integer defaultValue) {
        return (getInt(propertyName) != null) ? getInt(propertyName) : defaultValue;
    }

    @Override
    public Long getLong(String propertyName, Long defaultValue) {
        return (getLong(propertyName) != null) ? getLong(propertyName) : defaultValue;
    }

    @Override
    public Double getDouble(String propertyName, Double defaultValue) {
        return (getDouble(propertyName) != null) ? getDouble(propertyName) : defaultValue;
    }

    @Override
    public Boolean getBoolean(String propertyName, Boolean defaultValue) {
        return (getBoolean(propertyName) != null) ? getBoolean(propertyName) : defaultValue;
    }

    @Override
    public Character getCharacter(String propertyName, Character defaultValue) {
        return (getCharacter(propertyName) != null) ? getCharacter(propertyName) : defaultValue;
    }

    @Override
    public Byte getByte(String propertyName, Byte defaultValue) {
        return (getByte(propertyName) != null) ? getByte(propertyName) : defaultValue;
    }

    @Override
    public Object getObject(String propertyName, Object defaultValue) {
        return (getObject(propertyName) != null) ? getObject(propertyName) : defaultValue;
    }

    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }
}
