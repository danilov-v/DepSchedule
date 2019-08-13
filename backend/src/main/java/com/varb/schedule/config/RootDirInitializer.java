package com.varb.schedule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.NonNull;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class RootDirInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String BACKEND_PROJECT_DIRECTORY = "backend";

    /**
     * Програмно прописываем свойство 'project.basedir' в PropertySource
     */
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext appCtx) {
        String absoluteProjectPathStr = getAbsoluteProjectPath();

        Map<String, Object> propertyMap = Map.of("project.basedir", absoluteProjectPathStr);
        appCtx.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("custom-props", propertyMap));
    }

    private String getAbsoluteProjectPath() {
        try {
            File backendProjectDir = new File(getClass().getResource("/").toURI());

            for (int i = 0; !backendProjectDir.getName().equals(BACKEND_PROJECT_DIRECTORY); i++) {
                backendProjectDir = backendProjectDir.getParentFile();

                if (i > 10)
                    throw new IllegalStateException("The backend directory of the project was not found! ");
            }

            String rootProjectDir = backendProjectDir.getParentFile().getAbsolutePath();
            log.info("RootProjectDir: " + rootProjectDir);

            return rootProjectDir;

        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    //TODO как искать файл бд при запуске jar ?
}