package com.varb.schedule.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext appCtx) {
        try {
            File pwd = new File(getClass().getResource("/").toURI());

            File absoluteProjectPath = pwd.getParentFile().getParentFile().getParentFile();
            while (!absoluteProjectPath.getName().equals("DepSchedule"))
                absoluteProjectPath = absoluteProjectPath.getParentFile();

            String absoluteProjectPathStr = absoluteProjectPath.getAbsolutePath();
            System.out.println("absoluteProjectPath = " + absoluteProjectPathStr);

            Map<String, Object> props = new HashMap<>();
            props.put("project.basedir", absoluteProjectPathStr);
            MapPropertySource mapPropertySource = new MapPropertySource("custom-props", props);
            appCtx.getEnvironment().getPropertySources().addFirst(mapPropertySource);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}