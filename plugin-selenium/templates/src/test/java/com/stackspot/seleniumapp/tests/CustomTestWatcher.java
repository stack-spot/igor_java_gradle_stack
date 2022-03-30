package com.stackspot.seleniumapp.tests;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomTestWatcher implements TestWatcher {

    public static int errorCount;

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        WebDriver driver = AbstractTest.getWebDriver();
        try {
            // .replaceAll("[^a-zA-Z0-9]", "") = remove all the special characters apart from alpha numeric characters
            String testMethodName = extensionContext.getDisplayName().replaceAll("[^a-zA-Z0-9]", "");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.moveFile(scrFile, new File("./target/screenshots/ERROR " + (++errorCount) + " - " + testMethodName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AbstractTest.tearDownAfterEach();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        AbstractTest.tearDownAfterEach();
    }
}
