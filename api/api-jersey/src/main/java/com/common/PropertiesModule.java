package com.common;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.inject.name.Names.bindProperties;

/**
 * Loads properties file from "application.properties"
 * Usage: install(new PropertiesModule()) and use @Named properties
 *
 * To change filename, set environment variable "CONFIG__FILE"
 * To override any property, set environment variable with name as:
 *   Convert property name to uppercase, dot to double underscore
 *   e.g. "http_server.port" becomes "CONFIG__HTTP_SERVER__PORT"
 *
 * Keeping it simple for now, change as need arises:
 * One default file: "application.properties" for every project
 * Overrides only through environment, no system properties or cli params
 * Not using typesafe-config as it uses ugly property-file hacks for environment overrides
 */
public class PropertiesModule extends AbstractModule {

    private static final String DEFAULT_FILE = "application.properties";

    @Override
    protected void configure() {
        Map<String, String> properties = loadProperties();
        bindProperties(binder(), properties);
        bind(new TypeLiteral<Map<String, String>>() {}).annotatedWith(Names.named("properties")).toInstance(properties);
    }

    @SneakyThrows
    public static Map<String, String> loadProperties() {
        String propFile = DEFAULT_FILE;
        Properties props = new Properties();
        props.load(PropertiesModule.class.getClassLoader().getResourceAsStream(propFile));
        System.getenv().entrySet().stream()
                .filter(e -> !isNullOrEmpty(e.getValue()))
                .forEach(e -> props.setProperty(e.getKey(), e.getValue()));
        return Maps.fromProperties(props);
    }

}
