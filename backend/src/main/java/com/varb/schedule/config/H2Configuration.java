package com.varb.schedule.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * Configure H2 DataBase Server
 */
@Configuration
@Profile("h2Server")
public class H2Configuration {

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * Connect to "jdbc:h2:tcp://localhost:9092/file:/${pathToProject}/backend/src/main/resources/db/init/ScheduleDB".
     */
    @Bean
    @ConditionalOnExpression("${spring.h2.tcp.enabled:false}")
    public Server tcpServerStart(@Value("${spring.h2.tcp.port:9092}") String h2TcpPort) throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }

    /**
     * Web console for the embedded h2 database.
     *
     * Go to http://localhost:8082 and connect to the database "jdbc:h2:mem:testdb", username "sa", password empty.
     */
    @Bean
    @ConditionalOnExpression("${spring.h2.web.enabled:false}")
    public Server webServerStart(@Value("${spring.h2.web.port:8082}") String h2WebPort) throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
    }
}
