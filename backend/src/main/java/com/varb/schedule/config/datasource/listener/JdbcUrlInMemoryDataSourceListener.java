package com.varb.schedule.config.datasource.listener;

import java.util.Optional;

public class JdbcUrlInMemoryDataSourceListener extends DataSourceListener {
    private static final String DB_URL_PROP_NAME = "spring.datasource.jdbc-url";

    @Override
    String formatFilePath(String property) {
        return property;
    } //Сразу возвращаем url без проверок

    @Override
    Optional<String> getValueFromProp() {
        String dbUrl = getProperty(DB_URL_PROP_NAME);
        if (dbUrl != null && dbUrl.startsWith("jdbc:h2:mem")) { //Если база данных в оперативной памяти)
            return Optional.of(dbUrl);
        } else
            return Optional.empty();
    }
}
