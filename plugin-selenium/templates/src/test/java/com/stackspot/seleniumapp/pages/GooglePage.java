package com.stackspot.seleniumapp.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sample page
 */
public class GooglePage extends AbstractPage {

    @FindBy(xpath = "//input[@name='q']")
    private WebElement searchInput;

    /**
     * Invokes waitForPageToLoad
     * @param driver
     */
    public GooglePage(WebDriver driver) {
        super(driver);
    }

    /**
     * This page will be considered loaded when all the elements passed in the super.waitForPageToLoad method
     * become clickable
     * @return
     */
    @Override
    public AbstractPage waitForPageToLoad() {
        waitForPageToLoad(searchInput);
        return this;
    }

    public void searchFor(String term){
        searchInput.sendKeys(term, Keys.ENTER);
    }
}
