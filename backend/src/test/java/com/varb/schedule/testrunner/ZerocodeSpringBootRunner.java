package com.varb.schedule.testrunner;

import com.varb.schedule.ScheduleApplication;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

public class ZerocodeSpringBootRunner extends ZeroCodeUnitRunner {
    public static boolean appRunning = false;

    public ZerocodeSpringBootRunner(Class<?> klass) throws InitializationError {
        super(klass);
        if (!appRunning) {
            ScheduleApplication.main(new String[]{"--spring.profiles.active=test"});
            appRunning = true;
        }
    }
}
