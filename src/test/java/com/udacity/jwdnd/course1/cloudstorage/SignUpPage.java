package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Selenium Page Object: SignUpPage which repesents the page we are testing
 */

public class SignUpPage {

    // Define element selectors
    @FindBy(id = "inputFirstname")
    private WebElement inputFirstname;

    @FindBy(id = "inputLastname")
    private WebElement inputLastname;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    // Initializing elements in the constructor
    public SignUpPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    // Helper methods
    public void signUp(String firstname, String lastname, String username, String password) {
        this.inputFirstname.sendKeys(firstname);
        this.inputLastname.sendKeys(lastname);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();
    }
}
