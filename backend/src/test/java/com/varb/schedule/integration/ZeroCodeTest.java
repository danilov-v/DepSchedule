package com.varb.schedule.integration;

import com.varb.schedule.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@SpringBootTest
//@TestPropertySource(locations = "classpath:application.properties")
@TargetEnv("application_host.properties")
@RunWith(ZerocodeSpringBootRunner.class)
@Transactional
public class ZeroCodeTest {
    private static boolean initDataBeforeEventDurationTest_EXECUTED = false;
    private static boolean initDataBeforeEventTest_EXECUTED = false;

    @Test
    @JsonTestCase("integration_tests/eventType.json")
    public void eventType() {
    }

    @Test
    @JsonTestCase("integration_tests/unit.json")
    public void unit() {
    }

    public void initDataBeforeEventDurationTest() throws SQLException {
        if (!initDataBeforeEventDurationTest_EXECUTED)
            try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:db", "sa", "")) {
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scripts/InsertEventType.sql"));
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scripts/InsertUnit.sql"));
                initDataBeforeEventDurationTest_EXECUTED = true;
            }
    }

    @Test
    @JsonTestCase("integration_tests/eventDuration.json")
    public void eventDuration() {
    }

    public void initDataBeforeEventTest() throws SQLException {
        if (!initDataBeforeEventTest_EXECUTED) {
            initDataBeforeEventDurationTest();
            try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:db", "sa", "")) {
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scripts/InsertEventDuration.sql"));
            }
            initDataBeforeEventTest_EXECUTED = true;
        }
    }

    @Test
    @JsonTestCase("integration_tests/event.json")
    public void event() {
    }
}
