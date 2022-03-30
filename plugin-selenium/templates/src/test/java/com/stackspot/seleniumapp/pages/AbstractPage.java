package com.stackspot.seleniumapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.stackspot.seleniumapp.utils.SeleniumUtil;
import com.stackspot.seleniumapp.setup.SeleniumParams;

public abstract class AbstractPage {

    private WebDriver driver;
    private final long DEFAULT_ELEMENT_WAIT_TIME_IN_MILL = SeleniumParams.DEFAULT_ELEMENT_WAIT_TIME_IN_MILL();
    private final long DEFAULT_PAGE_WAIT_TIME_IN_MILL_IN_MILL = SeleniumParams.DEFAULT_PAGE_WAIT_TIME_IN_MILL();

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(getDriver(), this);
        waitForPageToLoad();
    }

    /**
     * @param <T>
     * @return
     */
    public abstract <T extends AbstractPage> T waitForPageToLoad();

    /**
     * Uses getDefaultPageLoadTime to wait for the visibility and 'clickability' of all the given elements
     * @param elementArray
     */
    protected void waitForPageToLoad(WebElement... elementArray) {
        SeleniumUtil.waitForElementsToBeClickable(getDriver(), elementArray, DEFAULT_PAGE_WAIT_TIME_IN_MILL_IN_MILL);
    }

    /**
     * Uses getDefaultPageLoadTime to wait for the visibility and 'clickability' of all the given elements
     * @param locatorArray
     */
    protected void waitForPageToLoad(By... locatorArray) {
        SeleniumUtil.waitForElementsToBeClickable(getDriver(), locatorArray, DEFAULT_PAGE_WAIT_TIME_IN_MILL_IN_MILL);
    }


    protected final WebDriver getDriver() {
        return driver;
    }

    protected final void clickOn(WebElement element) {
        SeleniumUtil.clickOn(getDriver(), element, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final boolean isElementShowing(By locator) {
        if (!elementExists(locator))
            return false;
        // low timeout because the element exists
        return SeleniumUtil.waitForPresenceOfElement(getDriver(), locator, 2000).isDisplayed();
    }

    protected final boolean elementExists(By locator) {
        return SeleniumUtil.elementExists(getDriver(), locator, 1000);
    }

    protected final WebElement scrollToElement(WebElement element) {
        SeleniumUtil.scrollToElement(getDriver(), element);
        return element;
    }

    protected final void scrollToTopAndClickOn(WebElement element) {
        SeleniumUtil.scrollToTop(getDriver());
        clickOn(waitForElementToBeClickable(element));
    }

    protected final void scrollToBottomAndClickOn(WebElement element) {
        SeleniumUtil.scrollToBottom(getDriver());
        clickOn(waitForElementToBeClickable(element));
    }

    protected final WebElement waitForPresenceOfElementLocated(By locator) {
        return SeleniumUtil.waitForPresenceOfElement(getDriver(), locator, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final WebElement waitForElementToBeClickable(WebElement element) {
        return SeleniumUtil.waitForElementToBeClickable(getDriver(), element, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final WebElement waitForElementToBeClickable(By locator) {
        return SeleniumUtil.waitForElementToBeClickable(getDriver(), locator, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL, 0);
    }

    protected final WebElement waitForElementToBeClickable(WebElement parent, By childLocator) {
        return SeleniumUtil.waitForElementToBeClickable(getDriver(), parent, childLocator, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final void waitForInvisibilityOf(By locator) {
        SeleniumUtil.waitForInvisibilityOf(getDriver(), locator, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final void waitForInvisibilityOf(WebElement element) {
        SeleniumUtil.waitForInvisibilityOf(getDriver(), element, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final void moveMouseOver(WebElement element) {
        SeleniumUtil.moveMouseOver(getDriver(), element);
    }

    protected final void setTextWhenTextAreaIsIframe(By iframeLocator, String description) {
        getDriver().switchTo().frame(getDriver().findElement(iframeLocator));
        WebElement descriptionBody = waitForElementToBeClickable(By.xpath("//body[@contenteditable='true']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].innerText = '" + description + "';", descriptionBody);
        getDriver().switchTo().defaultContent();
    }

    protected final void setTextWhenTextAreaIsIframe(WebElement iFrame, String description) {
        getDriver().switchTo().frame(iFrame);
        WebElement descriptionBody = waitForElementToBeClickable(By.xpath("//body[@contenteditable='true']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].innerText = '" + description + "';", descriptionBody);
        getDriver().switchTo().defaultContent();
    }

    protected final void doubleClick(WebElement element) {
        SeleniumUtil.doubleClick(getDriver(), element);
    }

    /**
     * Sets an element's inner html
     */
    protected final void setElementText(WebElement element, String text) {
        SeleniumUtil.setElementInnerHtml(getDriver(), element, text);
    }

    /**
     * Ensure a value is completely set to an element
     * @param element
     * @param value
     */
    protected final void setElementValue(WebElement element, String value) {
        clearElementValue(waitForElementToBeClickable(element));
        element.sendKeys(value);
    }

    protected final void setElementValue(By locator, String value) {
        setElementValue(waitForElementToBeClickable(locator), value);
    }

    protected final void clearElementValue(WebElement element) {
        element.clear();
        element.sendKeys("");
        SeleniumUtil.waitForElementTextToBe(getDriver(), element, "", DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
        SeleniumUtil.waitForElementAttributeToBe(getDriver(), element, "value", "", DEFAULT_ELEMENT_WAIT_TIME_IN_MILL);
    }

    protected final void clearElementValue(By locator) {
        clearElementValue(waitForElementToBeClickable(locator));
    }

    protected final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {

        }
    }

}
