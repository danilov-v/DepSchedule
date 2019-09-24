package com.varb.schedule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootSourceResolver {
    private final ConfigurableEnvironment environment;

    @Nullable
    private Boolean isBootFromJar;

    public boolean isBootFromJar() {
        if (isBootFromJar == null) {

            String classPath = environment.getProperty("java.class.path");
            if (classPath == null)
                throw new IllegalStateException("classPath=null");

            isBootFromJar = !classPath.contains("classes/java/main:");
        }

        return isBootFromJar;
    }
}
