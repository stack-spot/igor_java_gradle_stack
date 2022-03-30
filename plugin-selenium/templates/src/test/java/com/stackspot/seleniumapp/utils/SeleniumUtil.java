package com.stackspot.seleniumapp.utils;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public final class SeleniumUtil {

    public static synchronized WebElement waitForPresenceOfElement(WebDriver driver, By locator, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static synchronized WebElement waitForElementToBeClickable(WebDriver driver, WebElement parent, By childLocator, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementNotInteractableException.class);
        wait.ignoring(InvalidElementStateException.class);
        return wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return parent.findElement(childLocator);
            }
        });
    }

    public static synchronized WebElement waitForElementToBeClickable(WebDriver driver, By locator, long timeoutInMilliseconds, long intervalInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        if (intervalInMilliseconds > 0)
            wait.pollingEvery(Duration.ofMillis(intervalInMilliseconds));
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementNotInteractableException.class);
        wait.ignoring(InvalidElementStateException.class);
        return wait.until(ExpectedConditions.elementToBeClickable(locator)); // cliclkable = verifies enabled e visibility
    }

    public static synchronized WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementNotInteractableException.class);
        wait.ignoring(InvalidElementStateException.class);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static synchronized void waitForElementsToBeClickable(WebDriver driver, WebElement[] elements, long timeoutInMilliseconds) {
        for (WebElement element : elements) {
            waitForElementToBeClickable(driver, element, timeoutInMilliseconds);
        }
    }

    public static synchronized void waitForElementsToBeClickable(WebDriver driver, By[] locators, long timeoutInMilliseconds) {
        for (By locator : locators) {
            waitForElementToBeClickable(driver, locator, timeoutInMilliseconds, 0);
        }
    }

    public static synchronized void waitForPageTitleToBe(WebDriver driver, String title, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.until(ExpectedConditions.titleIs(title));
    }

    public static synchronized void setElementInnerHtml(WebDriver driver, WebElement element, String innerHtml) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].innerHTML = arguments[1]", element, innerHtml);
    }

    public static synchronized void scrollToBottom(WebDriver driver) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static synchronized void scrollToTop(WebDriver driver) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, 0);");
    }

    public static synchronized void scrollToElement(WebDriver driver, WebElement element) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static synchronized void scrollBy(WebDriver driver, long hPosition, long vPosition) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(" + hPosition + ", " + vPosition + ");");
    }

    public static synchronized void clickOnNotDisplayedElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static synchronized void switchDriverToLastOpenedWindow(WebDriver driver) {
        int windowsCount = driver.getWindowHandles().size();
        String lastOpenedWindow = String.valueOf(driver.getWindowHandles().toArray()[windowsCount - 1]);
        driver.switchTo().window(lastOpenedWindow);
    }

    public static synchronized void switchDriverToBaseWindow(WebDriver driver) {
        String baseWindow = String.valueOf(driver.getWindowHandles().toArray()[0]);
        driver.switchTo().window(baseWindow);
    }

    /**
     * Opens a new tab and switch driver to it
     * @param driver
     */
    public static synchronized void switchDriverToNewTab(WebDriver driver) {
        int windowCount = driver.getWindowHandles().size();
        // new tab
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.open();");
        waitForWindowsCountToBe(driver, windowCount + 1, 5000);
        switchDriverToLastOpenedWindow(driver);
    }


    public static synchronized boolean waitForElementTextToBe(WebDriver driver, WebElement element, String text,
            long timeoutInMilliseconds) {

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return element.getText().equals(text);
            }
        });
    }

    public static synchronized boolean waitForElementAttributeToBe(WebDriver driver, WebElement element, String atribute, String value,
            long timeoutInMilliseconds) {

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return element.getAttribute(atribute) != null && element.getAttribute(atribute).equals(value);
            }
        });
    }

    /**
     * Ignores "...not clickable at point" (ElementClickInterceptedException e WebDriverException)
     * Sometimes even the method waitForElementToBeClickable can't avoid such exceptions
     * @param driver
     * @param element
     * @param timeoutInMilliseconds
     * @return
     */
    public static synchronized boolean clickOn(WebDriver driver, WebElement element, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        wait.ignoring(ElementClickInterceptedException.class);
        return wait.until((ExpectedCondition<Boolean>) d -> {
            try {
                element.click();
                return true;
            } catch (Exception e) {
                if (e.getMessage().contains("is not clickable at point")) {
                    return false;
                } else {
                    throw e;
                }
            }
        });
    }

    public static synchronized void doubleClick(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
    }

    public static synchronized boolean waitForInvisibilityOf(WebDriver driver, By locator, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static synchronized boolean waitForInvisibilityOf(WebDriver driver, WebElement element, long timeoutInMilliseconds) {

        List<WebElement> list = new LinkedList<>();
        list.add(element);

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until(ExpectedConditions.invisibilityOfAllElements(list));
    }

    /**
     *
     * @param tableElement
     * @param value
     * @param ignoreCase
     * @return line elements (tr) that contains the given value (@param value) in its children's text() attribute
     * @Deprecated the xpath function 'translate' is not working as expected in some browsers when the string is like Some Assessment 12312342352
     */
    @Deprecated
    public static synchronized List<WebElement> getTableLineElementsThatContainsText(WebElement tableElement, String value, boolean ignoreCase) {

        List<WebElement> results;

        if (ignoreCase) {

            // remove special chars
            String formattedValue = value.trim().toLowerCase().replaceAll("\\s+", "");

            String lowerCaseAndNoWhiteSpacesXpath =
                    ".//tr[.//*[contains(translate(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), ' &#9;&#xA;&#xD;',''),'" + formattedValue + "')]]";

            results = tableElement.findElements(By.xpath(lowerCaseAndNoWhiteSpacesXpath));

        } else {

            String formattedValue = value.trim().replaceAll("\\s+", "");

            String noWhiteSpacesXpath = ".//tr[.//*[contains(translate(text(), ' &#9;&#xA;&#xD;',''),'" + formattedValue + "')]]";

            results = tableElement.findElements(By.xpath(noWhiteSpacesXpath));
        }

        return results;
    }

    /**
     *
     * @param tableElement
     * @param value
     * @return line elements (tr) that contains the given value (@param value) in its children's text() attribute
     */
    public static synchronized List<WebElement> getTableLineElementsThatContainsText(WebElement tableElement, String value) {

        List<WebElement> results;

        String xpath = ".//tr[.//*[contains(text(),'" + value + "')]]";

        results = tableElement.findElements(By.xpath(xpath));

        return results;
    }

    public static synchronized boolean elementExists(WebDriver driver, By locator, long timeoutInMilliseconds) {

        try {
            waitForPresenceOfElement(driver, locator, timeoutInMilliseconds);
            return true; // element found
        } catch (Exception e) {
        }
        return false;
    }

    public static synchronized void moveMouseOver(WebDriver driver, WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }


    public static synchronized Boolean waitForWindowsCountToBe(WebDriver driver, int windowCount, long timeoutInMilliseconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(windowCount));
    }

    /**
     * tries to set a value for timeoutInMilliseconds
     */
    public static synchronized boolean setElementValue(WebDriver driver, WebElement element, String value, long timeoutInMilliseconds) {
        element.clear();
        element.sendKeys(value);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds));
        return wait.until((ExpectedCondition<Boolean>) d -> {
            String currentElementValue = element.getAttribute("value");
            if (currentElementValue != null && value.equals(currentElementValue))
                return true;

            element.clear();
            element.sendKeys(value);
            return false;
        });
    }

}
