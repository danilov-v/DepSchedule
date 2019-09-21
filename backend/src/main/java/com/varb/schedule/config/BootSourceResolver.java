package com.varb.schedule.config;

public class BootSourceResolver {
    private BootSourceResolver() {
    }

    public static boolean isBootFromJar() {
        String classPath = RootDirAndEnvironmentInitializer.getEnvironment().getProperty("java.class.path");
        if (classPath == null)
            throw new IllegalStateException("classPath=null");
        return !classPath.contains("classes/java/main:");
    }
}
