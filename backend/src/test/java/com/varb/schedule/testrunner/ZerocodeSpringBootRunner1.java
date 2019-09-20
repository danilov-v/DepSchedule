package com.varb.schedule.testrunner;

import com.varb.schedule.ScheduleApplication;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks;

import javax.annotation.Nonnull;


public class ZerocodeSpringBootRunner1 extends ZeroCodeUnitRunner {
    private static final Logger logger =
            LoggerFactory.getLogger(ZerocodeSpringBootRunner.class);
    public static boolean appRunning = false;

    private final TestContextManager testContextManager;

    public ZerocodeSpringBootRunner1(Class<?> klass) throws InitializationError {
        super(klass);
        if (!appRunning) {
            ScheduleApplication.main(new String[]{"--spring.profiles.active=test"});
            appRunning = true;
        }
        this.testContextManager = new TestContextManager(klass);
    }

    protected final TestContextManager getTestContextManager() {
        return this.testContextManager;
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        final Description description = describeChild(method);
        JsonTestCase jsonTestCaseAnno = method.getMethod().getAnnotation(JsonTestCase.class);
        if (!isIgnored(method) && jsonTestCaseAnno != null) {
            try {
                Statement statement = methodBlock1(method);
                statement.evaluate();
            } catch (Throwable e) {
                logger.error("", e);
            }
        }
        super.runChild(method, notifier);
    }

    protected Statement methodBlock1(FrameworkMethod frameworkMethod) {
        Object testInstance;
        try {
            testInstance = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable ex) {
            return new Fail(ex);
        }

//        Statement statement = methodInvoker(frameworkMethod, testInstance);
//        statement = possiblyExpectingExceptions(frameworkMethod, testInstance, statement);
        Statement statement = withBefores(frameworkMethod, testInstance, getEmptyStatement());
        statement = withAfters(frameworkMethod, testInstance, statement);
//        statement = withRulesReflectively(frameworkMethod, testInstance, statement);
//        statement = withPotentialRepeat(frameworkMethod, testInstance, statement);
//        statement = withPotentialTimeout(frameworkMethod, testInstance, statement);
        return statement;
    }

    @Override
    protected Statement withBefores(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
        Statement junitBefores = super.withBefores(frameworkMethod, testInstance, statement);
        return new RunBeforeTestMethodCallbacks(junitBefores, testInstance, frameworkMethod.getMethod(), getTestContextManager());
    }

    @Nonnull
    private Statement getEmptyStatement() {
        return new Statement() {
            @Override
            public void evaluate() {
            }
        };
    }
}
