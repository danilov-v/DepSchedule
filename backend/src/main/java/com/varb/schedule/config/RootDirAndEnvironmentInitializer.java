package com.varb.schedule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class RootDirAndEnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String BACKEND_PROJECT_DIRECTORY = "backend";

    @Nullable
    private static ConfigurableEnvironment environment;

    /**
     * Програмно прописываем свойство 'project.basedir' в PropertySource
     */
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext appCtx) {
        environment = appCtx.getEnvironment();
        Map<String, Object> propertyMap = getAbsoluteProjectPath();
        environment.getPropertySources()
                .addFirst(new MapPropertySource("custom-props", propertyMap));
    }

    private Map<String, Object> getAbsoluteProjectPath() {
        boolean bootFromJar = BootSourceResolver.isBootFromJar();

        if (!bootFromJar) {
            String rootProjectDir = getAbsoluteProjectPathToBackendDir();
            log.info("RootDir: " + rootProjectDir);
            return Map.of("projectDir", rootProjectDir);
        } else {
            String jarDir = getAbsoluteProjectPathToJar();
            log.info("RootDir: " + jarDir);
            return Map.of("jarDir", jarDir);
        }
    }

    private String getAbsoluteProjectPathToJar() {
        try {
            File file = new DefaultResourceLoader().getResource("file:./").getFile();
            return file.getCanonicalPath();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getAbsoluteProjectPathToBackendDir() {
        try {
            File rootProjectDir = new DefaultResourceLoader().getResource("file:./").getFile();

            if (rootProjectDir.getPath().endsWith(BACKEND_PROJECT_DIRECTORY))
                rootProjectDir = rootProjectDir.getParentFile();

            return rootProjectDir.getCanonicalPath();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static ConfigurableEnvironment getEnvironment() {
        if (environment == null)
            throw new IllegalStateException("environment can not be null");
        return environment;
    }
}