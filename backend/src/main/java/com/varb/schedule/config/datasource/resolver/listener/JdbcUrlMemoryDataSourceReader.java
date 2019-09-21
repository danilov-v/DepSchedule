package com.varb.schedule.config.datasource.resolver.listener;

import java.util.Optional;

public class JdbcUrlMemoryDataSourceReader extends DataSourceReader {
    private static final String DB_URL_PROP_NAME = "spring.datasource.jdbc-url";

    @Override
    public String formatJdbcUrl(String property) {
        return property;
    } //Сразу возвращаем url без проверок

    @Override
    public Optional<String> getPathToDbFromProp() {
        String dbUrl = getProperty(DB_URL_PROP_NAME);
        if (dbUrl != null && dbUrl.startsWith("jdbc:h2:mem")) { //Если база данных в оперативной памяти)
            return Optional.of(dbUrl);
        } else
            return Optional.empty();
    }
}
