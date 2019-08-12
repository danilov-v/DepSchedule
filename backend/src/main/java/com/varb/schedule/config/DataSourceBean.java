package com.varb.schedule.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceBean {
    private static final String DB_URL_PROP_NAME = "spring.datasource.jdbc-url";
    private final ConfigurableApplicationContext applicationContext;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        dataSourceValidate();
        return DataSourceBuilder.create().build();
    }

    /**
     * Проверка существования файла базы данных
     */
    private void dataSourceValidate(){
        try {
            Environment env = applicationContext.getEnvironment();
            String dbUrl = env.getProperty(DB_URL_PROP_NAME);

            if (dbUrl == null)
                throw new IllegalStateException("Не удалось обнаружить свойство '" + DB_URL_PROP_NAME + "' в файле конфигурации");

            dbUrl = dbUrl.replace("jdbc:h2:file:", "") + ".mv.db";
            if (!new File(dbUrl).isFile())
                throw new FileNotFoundException("Не удалось обнаружить файл базы данных по пути " + dbUrl);

        } catch (Exception e) {
            log.error("", e);
            applicationContext.close();
            System.exit(0);
        }
    }
}