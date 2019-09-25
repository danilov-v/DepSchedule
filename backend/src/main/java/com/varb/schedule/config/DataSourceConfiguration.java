package com.varb.schedule.config;

import com.varb.schedule.config.datasource.resolver.DataSourceResolver;
import com.varb.schedule.config.datasource.resolver.listener.FilePathDatasourceReader;
import com.varb.schedule.config.datasource.resolver.listener.JdbcUrlFileDataSourceReader;
import com.varb.schedule.config.datasource.resolver.listener.JdbcUrlMemoryDataSourceReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {
    private final ConfigurableEnvironment environment;
    private final BootSourceResolver bootSourceResolver;

    @Value("${spring.liquibase.enabled:false}")
    private boolean isLiquibaseEnabled;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().url(getDbFileUrl()).build();
    }

    /**
     * Получение url к базе данных"
     */
    private String getDbFileUrl() {
        boolean checkDbFileExists = !isLiquibaseEnabled;

        String jdbcUrl = DataSourceResolver
                .use(new JdbcUrlMemoryDataSourceReader())
                .ifEmptyThen(new JdbcUrlFileDataSourceReader(checkDbFileExists))
                .ifEmptyThen(new FilePathDatasourceReader(checkDbFileExists, bootSourceResolver.isBootFromJar()))
                .getResult(environment);
        log.info("JDBC URL = "+jdbcUrl);
        return jdbcUrl;
    }
}