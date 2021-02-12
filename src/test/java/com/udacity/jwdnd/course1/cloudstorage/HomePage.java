package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Selenium Page Object: HomePage which represents the page we are testing
 */

public class HomePage {

    // Define element selectors
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    // Initializing elements in the constructor
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Helper methods
    public void logout() {
        logoutButton.click();
    }
}
