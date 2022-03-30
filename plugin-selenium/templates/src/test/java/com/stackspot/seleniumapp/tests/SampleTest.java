package com.stackspot.seleniumapp.tests;


import org.junit.jupiter.api.Test;

import com.stackspot.seleniumapp.pages.GooglePage;
import com.stackspot.seleniumapp.setup.SampleParams;

public class SampleTest extends AbstractTest {

    @Test
    public void test1() {
        getWebDriver().get(SampleParams.SAMPLE_URL);
        GooglePage googlePage = new GooglePage(getWebDriver());
        googlePage.searchFor("Chocolate Cake");
        // assert result..
    }
}
