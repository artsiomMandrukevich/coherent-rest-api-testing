package com.coherensolutions.rest.training.helpers;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesHelper {

    @SneakyThrows
    public Properties getAppProp() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        String rootPath = currentWorkingDir.normalize() + "/src/main/resources/";

        String appConfigPath = rootPath + "app.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        return appProps;
    }

}
