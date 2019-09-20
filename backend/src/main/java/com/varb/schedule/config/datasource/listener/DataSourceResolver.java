package com.varb.schedule.config.datasource.listener;

import org.springframework.lang.Nullable;

public class DataSourceResolver {
    @Nullable
    private DataSourceListener first;
    @Nullable
    private DataSourceListener last;

    public DataSourceResolver add(DataSourceListener dataSourceListener) {
        if (first == null) {
            first = dataSourceListener;
            last = dataSourceListener;
        }
        last.setNext(dataSourceListener);
        last = dataSourceListener;
        return this;
    }

    public String getResult() {
        if (first == null)
            throw new IllegalStateException("cannot be invoke 'getResult()' before at least one invoke 'add(DataSourceListener dataSourceListener)' ");
        return first.getDbFilePath();
    }
}
