package com.varb.schedule.config;

import com.varb.schedule.config.datasource.listener.DataSourceResolver;
import com.varb.schedule.config.datasource.listener.FilePathDatasourceListener;
import com.varb.schedule.config.datasource.listener.JdbcUrlFileDataSourceListener;
import com.varb.schedule.config.datasource.listener.JdbcUrlInMemoryDataSourceListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {
    @Value("${spring.liquibase.enabled:false}")
    private boolean isLiquibaseEnabled;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().url(getDbFileUrl()).build();
    }

    /**
     * Проверка конфигурации jdbc-url"
     */
    private String getDbFileUrl() {
        boolean checkFileExists = !isLiquibaseEnabled;

        String result = new DataSourceResolver()
                .add(new JdbcUrlInMemoryDataSourceListener())
                .add(new JdbcUrlFileDataSourceListener(checkFileExists))
                .add(new FilePathDatasourceListener(checkFileExists))
                .getResult();
        return result;
    }
}