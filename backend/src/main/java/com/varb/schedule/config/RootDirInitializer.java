package com.varb.schedule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class RootDirInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String BACKEND_PROJECT_DIRECTORY = "backend";

    /**
     * Програмно прописываем свойство 'projectDir' (или 'jarDir' если запускаем из jar) в PropertySource
     */
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext appCtx) {
        var environment = appCtx.getEnvironment();
        var bootSourceResolver = new BootSourceResolver(environment);
        var propertyMap = getAbsoluteProjectPath(bootSourceResolver.isBootFromJar());

        environment.getPropertySources()
                .addFirst(new MapPropertySource("custom-props", propertyMap));
    }

    private Map<String, Object> getAbsoluteProjectPath(boolean bootFromJar) {
        if (!bootFromJar) {
            String rootProjectDir = getPathToProjectDir();
            log.info("RootDir: " + rootProjectDir);
            return Map.of("projectDir", rootProjectDir);
        } else {
            String jarDir = getPathToJarDir();
            log.info("RootDir: " + jarDir);
            return Map.of("jarDir", jarDir);
        }
    }

    private String getPathToJarDir() {
        try {
            return new File("./").getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getPathToProjectDir() {
        try {
            String rootProjectDir = new File("./").getCanonicalPath();

            if (rootProjectDir.endsWith(BACKEND_PROJECT_DIRECTORY))
                rootProjectDir = new File(rootProjectDir).getParent();

            return rootProjectDir;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}