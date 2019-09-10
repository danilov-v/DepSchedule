package com.varb.schedule.testrunner;

import com.varb.schedule.ScheduleApplication;
import org.jsmart.zerocode.core.runner.ZeroCodePackageRunner;
import org.junit.runners.model.InitializationError;

import static com.varb.schedule.testrunner.ZerocodeSpringBootRunner.appRunning;

public class ZerocodeSpringBootSuite extends ZeroCodePackageRunner {

    static{
        // ---------------------------------------------------------------------------
        // appRunning flag is used only when we do a right-click on the "integration"
        // folder via an IDE and run the tests.
        //
        // Usage of appRunning flag is not necessary for Jenkins builds,
        // as Jenkins will run the tests either as a Suite or Individually,
        // but never both.
        // ---------------------------------------------------------------------------
        if(!appRunning){
            ScheduleApplication.main(new String[]{"--spring.profiles.active=test"});
            appRunning = true;
        }
    }

    public ZerocodeSpringBootSuite(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
