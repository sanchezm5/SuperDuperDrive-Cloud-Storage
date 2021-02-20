package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Selenium Page Object: CredentialsTabPage which represents the page we are testing
 */

public class CredentialsTabPage {

    // Define element selectors
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredential;

    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "edit-credential")
    private List<WebElement> editCredentialButton;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credentialUrl")
    private List<WebElement> urlList;

    @FindBy(id = "credentialUsername")
    private List<WebElement> usernameList;

    @FindBy(id = "credentialPassword")
    private List<WebElement> passwordList;

    @FindBy(id = "credential-url")
    private WebElement inputUrl;

    @FindBy(id = "credential-username")
    private WebElement inputUsername;

    @FindBy(id = "credential-password")
    private WebElement inputPassword;

    @FindBy(id = "credential-modal-submit")
    private WebElement submitModalButton;

    // Initializing elements in the constructor
    public CredentialsTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Helper Methods
    public List<String> getCredDetails(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        navCredential.click();
        wait.until(ExpectedConditions.visibilityOf(addCredentialButton));
        List<String> detail = new ArrayList<>(List.of(
                urlList.get(0).getText(),
                usernameList.get(0).getText(),
                passwordList.get(0).getText()));
        return detail;
    }

    public void addCredential(WebDriver driver, String url, String username, String password, WebElement nav) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception");
            nav.click();
            wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        }
        wait.until(ExpectedConditions.visibilityOf(addCredentialButton)).click();
        wait.until(ExpectedConditions.visibilityOf(inputUrl)).sendKeys(url);
        wait.until(ExpectedConditions.visibilityOf(inputUsername)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
    }

    public void editCredential(WebDriver driver, String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        wait.until(ExpectedConditions.visibilityOf(editCredentialButton.get(0))).click();
        wait.until(ExpectedConditions.visibilityOf(inputUrl));
        inputUrl.clear();
        inputUrl.sendKeys(url);
        wait.until(ExpectedConditions.visibilityOf(inputUsername));
        inputUsername.clear();
        inputUsername.sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(inputPassword));
        inputPassword.clear();
        inputPassword.sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void deleteCredential(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        wait.until(ExpectedConditions.visibilityOf(deleteCredentialButton)).click();
    }
}