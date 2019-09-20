package com.varb.schedule.config.datasource.listener;

import com.varb.schedule.config.BootSourceResolver;

import java.util.Optional;

public class FilePathDatasourceListener extends FileDataSourceListener {
    private static final String DB_FILE_PATH_PROP_NAME = "spring.datasource.filePath";
    private static final String DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME = "spring.datasource.filePathInProjectDir";

    public FilePathDatasourceListener(boolean checkFileExists) {
        super(checkFileExists);
    }

    @Override
    Optional<String> getValueFromProp() {
        String dbUrl;

        if (BootSourceResolver.isBootFromJar())
            dbUrl = getProperty(DB_FILE_PATH_PROP_NAME);
        else
            dbUrl = getProperty(DB_FILE_PATH_IN_PROJECT_DIR_PROP_NAME);

        return Optional.ofNullable(dbUrl);
    }

}
