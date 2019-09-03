package com.varb.schedule.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {
    private static final String DB_URL_PROP_NAME = "spring.datasource.jdbc-url";
    private final ConfigurableApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        dbUrlValidate();
        return DataSourceBuilder.create().build();
    }

    /**
     * Проверка конфигурации jdbc-url"
     */
    private void dbUrlValidate() {
        try {
            Environment env = applicationContext.getEnvironment();
            String dbUrl = env.getProperty(DB_URL_PROP_NAME);

            if (dbUrl == null)
                throw new IllegalStateException("Не удалось обнаружить свойство '" + DB_URL_PROP_NAME + "' в файле конфигурации");

            if (dbUrl.startsWith("jdbc:h2:file"))//Если база данных сохраняется в файл
                dbFileValidate(dbUrl);

        } catch (Exception e) {
            log.error("", e);
            applicationContext.close();
            System.exit(0);
        }
    }

    /**
     * проверяем существование файла базы данных
     * @param dbUrl JDBC connection url
     * @throws FileNotFoundException если не найден файл базы данных
     */
    private void dbFileValidate(String dbUrl) throws FileNotFoundException {
        dbUrl = dbUrl.replace("jdbc:h2:file:", "") + ".mv.db";
        if (!new File(dbUrl).isFile())
            throw new FileNotFoundException("Не удалось обнаружить файл базы данных по пути " + dbUrl);

        log.info("DbStorageFilePath: " + dbUrl);
    }
}