package com.varb.schedule.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextInitializerRootDir implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String PROJECT_ROOT_DIRECTORY = "DepSchedule";

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext appCtx) {
        try {
            File pwd = new File(getClass().getResource("/").toURI()); // .../DepSchedule/backend/out/production/classes

            File absoluteProjectPath = pwd.getParentFile().getParentFile().getParentFile();
            for (int i = 0; !absoluteProjectPath.getName().equals(PROJECT_ROOT_DIRECTORY); i++) {
                absoluteProjectPath = absoluteProjectPath.getParentFile();
                if (i > 10)
                    throw new RuntimeException("The root directory of the project was not found! " +
                            "(Expected root directory = " + PROJECT_ROOT_DIRECTORY + ")");
            }

            String absoluteProjectPathStr = absoluteProjectPath.getAbsolutePath();

            System.out.println("absoluteProjectPath="+absoluteProjectPathStr);

            Map<String, Object> propertyMap = Map.of("project.basedir", absoluteProjectPathStr);
            appCtx.getEnvironment().getPropertySources()
                    .addFirst(new MapPropertySource("custom-props", propertyMap));

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}