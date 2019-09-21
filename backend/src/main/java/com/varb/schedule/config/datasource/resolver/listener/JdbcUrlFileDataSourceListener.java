package com.varb.schedule.config.datasource.resolver.listener;

import org.springframework.lang.Nullable;

import java.util.Optional;

public class JdbcUrlFileDataSourceListener extends FileDataSourceListener {
    private static final String DB_JDBC_URL_PROP_NAME = "spring.datasource.jdbc-url";
    private static final String DB_URL_PROP_NAME = "spring.datasource.url";

    public JdbcUrlFileDataSourceListener(boolean checkFileExists) {
        super(checkFileExists);
    }

    @Override
    public Optional<String> getPathToDbFromProp() {
        String dbUrl = getProperty();
        if (dbUrl != null && dbUrl.startsWith("jdbc:h2:file")) { //Если база данных в файле
            return Optional.of(dbUrl);
        } else
            return Optional.empty();
    }

    @Nullable
    private String getProperty() {
        String jdbcUrlPropName = getProperty(DB_JDBC_URL_PROP_NAME);
        String urlPropName = getProperty(DB_URL_PROP_NAME);
        if (jdbcUrlPropName != null && urlPropName != null) {
            throw new IllegalStateException("You must define only one property: 'spring.datasource.url' or 'spring.datasource.jdbc-url'");
        }

        return jdbcUrlPropName != null ? jdbcUrlPropName : urlPropName;
    }
}
