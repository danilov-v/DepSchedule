package com.varb.schedule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ApplicationListenerContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static final String DB_URL_PROP_NAME = "spring.datasource.url";

    /**
     * Проверка существования файла базы данных
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        try {
            Environment env = applicationContext.getEnvironment();
            String dbUrl = env.getProperty(DB_URL_PROP_NAME);

            if (dbUrl == null)
                throw new RuntimeException("Не удалось обнаружить свойство '" + DB_URL_PROP_NAME + "' в файле конфигурации");

            dbUrl = dbUrl.replace("jdbc:h2:file:", "") + ".mv.db";
            if (!new File(dbUrl).isFile())
                throw new RuntimeException("Не удалось обнаружить файл базы данных по пути " + dbUrl);

        } catch (RuntimeException e) {
            log.error("", e);
            ((ConfigurableApplicationContext) applicationContext).close();
            System.exit(0);
        }
    }
}