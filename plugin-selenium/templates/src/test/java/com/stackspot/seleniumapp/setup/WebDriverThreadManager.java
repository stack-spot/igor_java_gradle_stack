package com.stackspot.seleniumapp.setup;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverThreadManager {

    // Holds the WebDriver instance for each thread
    private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();

    public static synchronized WebDriver getDriverInstance(String browser, long threadId) {
        WebDriver driver = map.get(threadId);
        if (driver == null) {
            driver = startDriver(browser, threadId);
        }
        return driver;
    }

    private static synchronized WebDriver startDriver(String browser, long threadId) {
        WebDriver newDriver;

        if (browser == null || browser.isEmpty())
            browser = "CHROME";

        switch (browser) {
            case "HEADLESS-CHROME":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--lang=pt-BR");
                options.addArguments("--disable-gpu");

                
                options.addArguments("start-maximized");
                options.addArguments("enable-automation"); 
                options.addArguments("--no-sandbox"); 
                options.addArguments("--disable-infobars"); 
                options.addArguments("--disable-dev-shm-usage"); 
                options.addArguments("--disable-browser-side-navigation"); 

                newDriver = new ChromeDriver(options);
                break;
            case "IE":
                WebDriverManager.iedriver().setup(); // webdrivermanager 3.0.0
                newDriver = new InternetExplorerDriver();
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("intl.accept_languages", "pt-br");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProfile(firefoxProfile);
                newDriver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                options.addArguments("disable-infobars");
                options.addArguments("--lang=pt-BR");
                newDriver = new ChromeDriver(options);
                break;

        }

        map.put(threadId, newDriver);
        return newDriver;
    }

    public static synchronized void stopDriver(long threadId) {
        WebDriver driver = map.get(threadId);
        if (driver != null) {
            try {
                driver.close();
                try {
                    driver.quit();
                } catch (Throwable t) {
                }
            } catch (Throwable t) {
            }

            map.remove(threadId);
        }
    }
}
