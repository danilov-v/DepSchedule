package com.varb.schedule.config.datasource.resolver.listener;

import com.varb.schedule.config.BootSourceResolver;

import java.util.Optional;

public class FilePathDatasourceListener extends FileDataSourceListener {
    private static final String DB_FILE_PATH_IN_JAR_DIR_PROP_NAME = "spring.datasource.filePathInJarDir";
    private static final String DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME = "spring.datasource.filePathInProjectDir";

    public FilePathDatasourceListener(boolean checkFileExists) {
        super(checkFileExists);
    }

    @Override
    public Optional<String> getPathToDbFromProp() {
        String dbUrl;

        if (BootSourceResolver.isBootFromJar())
            dbUrl = getProperty(DB_FILE_PATH_IN_JAR_DIR_PROP_NAME);
        else
            dbUrl = getProperty(DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME);

        return Optional.ofNullable(dbUrl);
    }

}
