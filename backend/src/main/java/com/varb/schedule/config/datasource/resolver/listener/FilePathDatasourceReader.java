package com.varb.schedule.config.datasource.resolver.listener;

import java.util.Optional;

public class FilePathDatasourceReader extends FileDataSourceReader {
    private static final String DB_FILE_PATH_IN_JAR_DIR_PROP_NAME = "spring.datasource.filePathInJarDir";
    private static final String DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME = "spring.datasource.filePathInProjectDir";
    private final boolean isBootFromJar;

    public FilePathDatasourceReader(boolean checkFileExists, boolean isBootFromJar) {
        super(checkFileExists);
        this.isBootFromJar = isBootFromJar;
    }

    @Override
    public Optional<String> getPathToDbFromProp() {
        String dbUrl;

        if (isBootFromJar)
            dbUrl = getProperty(DB_FILE_PATH_IN_JAR_DIR_PROP_NAME);
        else
            dbUrl = getProperty(DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME);

        return Optional.ofNullable(dbUrl);
    }

}
