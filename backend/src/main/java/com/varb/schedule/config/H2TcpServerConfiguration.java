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
public class H2TcpServerConfiguration {

    public H2TcpServerConfiguration(@Value("${h2.tcp.port:9092}") String h2TcpPort) {
        this.h2TcpPort = h2TcpPort;
    }

    // TCP port for remote connections, default 9092
    private final String h2TcpPort;

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * Connect to "jdbc:h2:tcp://localhost:9092/file:/${pathToProject}/backend/src/main/resources/db/init/ScheduleDB".
     */
    @Bean
    @ConditionalOnExpression("${spring.h2.tcp.enabled:false}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }
}
