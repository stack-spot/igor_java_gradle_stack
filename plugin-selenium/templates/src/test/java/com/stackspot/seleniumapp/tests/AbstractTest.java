package com.stackspot.seleniumapp.tests;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.stackspot.seleniumapp.setup.SeleniumParams;
import com.stackspot.seleniumapp.setup.WebDriverThreadManager;

@ExtendWith(CustomTestWatcher.class)
public abstract class AbstractTest {

    private static WebDriver webDriver;
    private static final long DEFAULT_PAGE_WAIT_TIME_IN_MILL = SeleniumParams.DEFAULT_PAGE_WAIT_TIME_IN_MILL();
    private static final long DEFAULT_ELEMENT_WAIT_TIME_IN_MILL = SeleniumParams.DEFAULT_ELEMENT_WAIT_TIME_IN_MILL();
    private static int ID = 1; // no multi-thread support for now

    public static WebDriver getWebDriver() {
        return webDriver;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        initDriver();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        // ex: login operations
    }

    /**
     * @AfterEach behavior is ignored here and is implemented in  CustomTestWatcher.testSuccessful.
     * Reason: a method called by @AfterEach gets executed before CustomTestWatcher.testFailed, thus hindering
     * the taking screen shot process of CustomTestWatcher.testFailed
     */
    /* @AfterEach */
    public static void tearDownAfterEach() {
        // ex: do logout ...
    }

    @AfterAll
    public static void tearDown() {
        WebDriverThreadManager.stopDriver(ID);
        webDriver = null; // so each test class will start a fresh webdriver
    }


    private static void initDriver(){
        String browserName = SeleniumParams.BROWSER();

        if (webDriver == null) {
            webDriver = WebDriverThreadManager.getDriverInstance(browserName, ID);
            webDriver.manage().timeouts().pageLoadTimeout(DEFAULT_PAGE_WAIT_TIME_IN_MILL, TimeUnit.MILLISECONDS);

            if (SeleniumParams.MONITOR_ID() == 1)
                webDriver.manage().window().setPosition(new Point(0, 0));
            else
                webDriver.manage().window().setPosition(new Point(2000, 1));

            webDriver.manage().window().maximize();
        }
    }
}
