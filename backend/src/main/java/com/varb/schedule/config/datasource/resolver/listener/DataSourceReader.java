package com.varb.schedule.config.datasource.resolver.listener;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DataSourceReader {
    protected final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private List<String> checkedPropNames = new ArrayList<>();

    @Nullable
    private ConfigurableEnvironment environment;

    public void setEnvironment(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Nullable
    final String getProperty(String propName) {
        if (environment==null)
            throw new NullPointerException("environment = null! You must call 'setEnvironment(ConfigurableEnvironment environment)' method " +
                    "before use this class!");

        checkedPropNames.add(propName);
        return environment.getProperty(propName);
    }

    public List<String> getCheckedPropNames() {
        return checkedPropNames;
    }

    public abstract String formatJdbcUrl(String property);

    public abstract Optional<String> getPathToDbFromProp();

}
