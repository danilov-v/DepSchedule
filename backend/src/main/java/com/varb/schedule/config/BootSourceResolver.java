package com.varb.schedule.config;

import org.springframework.core.env.Environment;

public class BootSourceResolver {
    private BootSourceResolver() {
    }

    public static boolean isBootFromJar() {
        Environment env = RootDirAndEnvironmentInitializer.getEnvironment();
        String classPath = env.getProperty("java.class.path");
        if (classPath == null)
            throw new IllegalStateException("classPath=null");
        return !classPath.contains("classes/java/main:");
    }
}
