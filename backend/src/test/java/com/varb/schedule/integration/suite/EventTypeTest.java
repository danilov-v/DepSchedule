package com.varb.schedule.integration.suite;

import com.varb.schedule.testrunner.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

@TargetEnv("application_host.properties")
@RunWith(ZerocodeSpringBootRunner.class)
public class EventTypeTest {

    @Test
    @JsonTestCase("integration_tests/eventType.json")
    public void eventType() {
    }

}
