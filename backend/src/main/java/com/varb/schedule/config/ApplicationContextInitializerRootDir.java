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
public class ApplicationContextInitializerRootDir implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String PROJECT_ROOT_DIRECTORY = "DepSchedule";

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

            File absoluteProjectPath = new File(getClass().getResource("/").toURI());
            for (int i = 0; !absoluteProjectPath.getName().equals(PROJECT_ROOT_DIRECTORY); i++) {
                absoluteProjectPath = absoluteProjectPath.getParentFile();
                if (i > 10)
                    throw new IllegalStateException("The root directory of the project was not found! " +
                            "(Expected root directory = " + PROJECT_ROOT_DIRECTORY + ")");
            }

            String absoluteProjectPathStr = absoluteProjectPath.getAbsolutePath();
            log.info("absoluteProjectPath=" + absoluteProjectPathStr);
            return absoluteProjectPathStr;

        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    //TODO как искать файл бд при запуске jar ?
}