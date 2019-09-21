package com.varb.schedule.config.datasource.resolver.listener;

import com.varb.schedule.config.RootDirAndEnvironmentInitializer;
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
    protected final String getProperty(String propName) {
        checkedPropNames.add(propName);
        return RootDirAndEnvironmentInitializer.getEnvironment().getProperty(propName);
    }

    public List<String> getCheckedPropNames() {
        return checkedPropNames;
    }

    public abstract String formatJdbcUrl(String property);

    public abstract Optional<String> getPathToDbFromProp();

}
