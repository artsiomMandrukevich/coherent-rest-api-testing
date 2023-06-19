package com.coherensolutions.rest.training.helpers;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesHelper {

    @SneakyThrows
    public static Properties getAppProperties() {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "app.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        return appProps;
    }
}
