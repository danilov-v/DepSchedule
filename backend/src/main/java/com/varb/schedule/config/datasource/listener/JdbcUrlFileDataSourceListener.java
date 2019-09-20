package com.varb.schedule.config.datasource.listener;

import java.util.Optional;

public class JdbcUrlFileDataSourceListener extends FileDataSourceListener {
    private static final String DB_URL_PROP_NAME = "spring.datasource.jdbc-url";

    public JdbcUrlFileDataSourceListener(boolean checkFileExists) {
        super(checkFileExists);
    }

    @Override
    Optional<String> getValueFromProp() {
        String dbUrl = getProperty(DB_URL_PROP_NAME);
        if (dbUrl != null && dbUrl.startsWith("jdbc:h2:file")) { //Если база данных в файле)
            return Optional.of(dbUrl);
        } else
            return Optional.empty();
    }
}
