package com.varb.schedule;

import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

public class ZerocodeSpringBootRunner extends ZeroCodeUnitRunner {
    public ZerocodeSpringBootRunner(Class<?> klass) throws InitializationError {
        super(klass);
        ScheduleApplication.main(new String[]{"--spring.profiles.active=test"});
    }
}
