package com.varb.schedule.integration;

import com.varb.schedule.testrunner.ZerocodeSpringBootSuite;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.domain.TestPackageRoot;
import org.junit.runner.RunWith;

@TargetEnv("application_host.properties")
@TestPackageRoot("integration_tests") //picks all tests from this folder and subfolders
@RunWith(ZerocodeSpringBootSuite.class)
public class IntegrationTestSuite {

}
