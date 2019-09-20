package com.varb.schedule.integration.suite;

import com.varb.schedule.testrunner.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TargetEnv("application_host.properties")
@RunWith(ZerocodeSpringBootRunner.class)
//@SqlGroup({
//        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts =
//                "classpath:cleanup.sql")
//})
public class EventTest {

    @BeforeClass
    //@Sql("/db/scripts/initEventTest.sql")
    public static void before() {
        System.out.println("123---");
    }

    @Test
    @JsonTestCase("integration_tests/event.json")
    public void event() {
    }
}
