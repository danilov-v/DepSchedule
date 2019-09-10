package com.varb.schedule.integration.suite;

import com.varb.schedule.testrunner.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@TargetEnv("application_host.properties")
@RunWith(ZerocodeSpringBootRunner.class)
//@SqlGroup({
//        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts =
//                "classpath:cleanup.sql")
//})
public class EventTest {

    @Test
    @JsonTestCase("integration_tests/event.json")
    public void event() {

    }
}
