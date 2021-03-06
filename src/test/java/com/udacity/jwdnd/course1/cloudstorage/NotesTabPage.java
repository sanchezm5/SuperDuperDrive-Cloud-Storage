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
 * Selenium Page Object: NotesTabPage which represents the page we are testing
 */

public class NotesTabPage {

    // Define element selectors
    @FindBy(id = "nav-notes-tab")
    private WebElement navNote;

    @FindBy(id = "add-note")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note")
    private List<WebElement> editNoteButton;

    @FindBy(id = "delete-note")
    private List<WebElement> deleteNoteButton;

    @FindBy(id = "notetitle")
    private List<WebElement> titleList;

    @FindBy(id = "notedescription")
    private List<WebElement> descriptionList;

    @FindBy(id = "note-title")
    private WebElement inputTitle;

    @FindBy(id = "note-description")
    private WebElement inputDescription;

    @FindBy(id = "note-modal-submit")
    private WebElement submitModalButton;

    // Initializing elements in the constructor
    public NotesTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Helper Methods
    public List<String> getDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(navNote)).click();
        navNote.click();
        wait.until(ExpectedConditions.visibilityOf(addNoteButton));
        List<String> detail = new ArrayList<>(List.of(titleList.get(0).getText(),
                descriptionList.get(0).getText()));
        return detail;
    }

    public void addNote(WebDriver driver, String title, String description, WebElement nav) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.visibilityOf(navNote)).click();
        } catch (TimeoutException e) {
            nav.click();
            wait.until(ExpectedConditions.visibilityOf(navNote)).click();
        }
        wait.until(ExpectedConditions.visibilityOf(addNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOf(inputTitle)).sendKeys(title);
        wait.until(ExpectedConditions.visibilityOf(inputDescription)).sendKeys(description);
        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void editNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(navNote)).click();
        wait.until(ExpectedConditions.visibilityOf((editNoteButton.get(0)))).click();
        wait.until(ExpectedConditions.visibilityOf(inputTitle));
        inputTitle.clear();
        inputTitle.sendKeys(title);
        wait.until(ExpectedConditions.visibilityOf(inputDescription));
        inputDescription.clear();
        inputDescription.sendKeys(description);
        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void deleteNote(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(navNote)).click();
        wait.until(ExpectedConditions.visibilityOf(deleteNoteButton.get(0))).click();
    }
}


