package com.varb.schedule.config.datasource.resolver;

import com.varb.schedule.config.datasource.resolver.listener.DataSourceListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataSourceResolver {
    private List<DataSourceListener> listeners = new ArrayList<>();
    private List<String> checkedPropNames = new ArrayList<>();

    public DataSourceResolverResult add(DataSourceListener dataSourceListener) {
        addListener(dataSourceListener);
        return new DataSourceResolverResult();
    }

    private void addListener(DataSourceListener dataSourceListener) {
        listeners.add(dataSourceListener);
    }

    public class DataSourceResolverResult {
        public DataSourceResolverResult add(DataSourceListener dataSourceListener) {
            addListener(dataSourceListener);
            return this;
        }

        public String getResult() {
            assert !listeners.isEmpty();

            for (DataSourceListener listener : listeners) {
                Optional<String> prop = listener.getPathToDbFromProp();
                checkedPropNames.addAll(listener.getCheckedPropNames());
                if (prop.isPresent()) {
                    return listener.formatJdbcUrl(prop.get());
                }
            }

            throw new IllegalStateException("Не найден путь к файлу базы данных в свойствах [\n"
                    + String.join(",", checkedPropNames)
                    + "\n]");
        }
    }


}
