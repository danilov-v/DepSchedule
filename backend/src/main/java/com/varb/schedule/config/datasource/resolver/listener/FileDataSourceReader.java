package com.varb.schedule.config.datasource.resolver.listener;

import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public abstract class FileDataSourceReader extends DataSourceReader {
    private final boolean checkFileExists;

    public FileDataSourceReader(boolean checkFileExists) {
        this.checkFileExists = checkFileExists;
    }

    @Value("${spring.liquibase.enabled:false}")
    private boolean isLiquibaseEnabled;

    @Override
    public String formatJdbcUrl(String property) {
        String dbPath = property.replace("jdbc:h2:", "");

        String resolvedFilePath = formatFilePath(dbPath, checkFileExists);

        return "jdbc:h2:file:" + resolvedFilePath;
    }

    private String formatFilePath(String defaultSearchLocations, boolean checkFileExists) {
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(defaultSearchLocations);
            String path = fileSystemResource.getPath();
            if (checkFileExists && !fileSystemResource.exists())
                throw new FileNotFoundException("Не удалось обнаружить файл базы данных по пути: " + path);
            return path;

        } catch (IOException e) {
            throw new ServiceException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
