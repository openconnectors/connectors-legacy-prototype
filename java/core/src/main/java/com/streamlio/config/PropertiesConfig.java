package com.streamlio.config;

import com.streamlio.localfs.LineSpout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertiesConfig implements Config {

    private static final Logger LOG = LoggerFactory.getLogger(LineSpout.class);

    final Properties properties;

    public PropertiesConfig() throws IOException {
        this.properties = new Properties();
        this.properties.load(PropertiesConfig.class.getClassLoader()
                .getResourceAsStream("connector.properties"));
        Properties local = new Properties();
        local.load(PropertiesConfig.class.getClassLoader()
                    .getResourceAsStream("local.connector.properties"));
        this.merge(local);
    }

    @Override
    public String getString(String propertyName) {
        if (isNull(propertyName, properties)) return null;
        return properties.get(propertyName).toString();
    }

    @Override
    public Integer getInt(String propertyName) {
        if (isNull(propertyName, properties)) return null;
        return Integer.parseInt(properties.get(propertyName).toString());
    }

    @Override
    public Long getLong(String propertyName) {
        if (isNull(propertyName, properties)) return null;
        return Long.parseLong(properties.get(propertyName).toString());
    }

    @Override
    public Double getDouble(String propertyName) {
        if (isNull(propertyName, properties)) return null;
        return Double.parseDouble(properties.get(propertyName).toString());
    }

    @Override
    public Boolean getBoolean(String propertyName) {
        if (isNull(propertyName, properties)) return null;
        return Boolean.parseBoolean(properties.get(propertyName).toString());
    }

    @Override
    public Character getCharacter(String propertyName) {
        if (isNull(propertyName, properties)) return null;
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

    private void merge(final Properties p){
        this.properties.putAll(p);
    }

    private static boolean isNull(String propertyName, Properties properties) {
        if(properties == null || propertyName == null || properties.containsKey(propertyName) == false){
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getPropertyKeys() {
        if(properties == null){
            return null;
        }

        return properties.keySet().stream().map(x -> (String) x).collect(Collectors.toSet());
    }
}
