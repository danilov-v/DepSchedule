package com.varb.schedule.config.datasource.listener;

import com.varb.schedule.config.RootDirAndEnvironmentInitializer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class DataSourceListener {
    protected final ResourceLoader resourceLoader = new DefaultResourceLoader();

    private List<String> checkedPropNames = new ArrayList<>();
    @Nullable
    private DataSourceListener next;
    @Nullable
    private String dbFilePath;

    void setNext(DataSourceListener dataSourceListener) {
        next = dataSourceListener;
    }

    private DataSourceListener doNext() {
        if (next != null)
            return next.checkAndValidate(this);
        else
            return this;
    }

    public String getDbFilePath() {
        DataSourceListener dataSourceListener = checkAndValidate();
        if (dataSourceListener.dbFilePath == null) {
            throw new IllegalStateException("Не найден путь к файлу базы данных в свойствах [\n"
                    + String.join(",", checkedPropNames)
                    + "\n]");
        }

        return dbFilePath;
    }

    public List<String> getCheckedPropNames() {
        return checkedPropNames;
    }

    private DataSourceListener checkAndValidate(DataSourceListener dataSourceListener) {
        this.checkedPropNames.addAll(dataSourceListener.checkedPropNames);
        return checkAndValidate();
    }

    private DataSourceListener checkAndValidate() {
        Optional<String> prop = getValueFromProp();
        if (prop.isPresent()) {
            dbFilePath = formatFilePath(prop.get());
            return this;
        } else {
            return doNext();
        }
    }

    @Nullable
    protected String getProperty(String propName) {
        checkedPropNames.add(propName);
        return RootDirAndEnvironmentInitializer.getEnvironment().getProperty(propName);
    }

    abstract String formatFilePath(String property);

    abstract Optional<String> getValueFromProp();

}
