package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignUpPage signup;
	private LoginPage login;
	private HomePage home;
	private NotesTabPage notes;
	private CredentialsTabPage creds;
	private WebDriverWait wait;

	String firstname = "Person";
	String lastname = "Tester";
	String username = "TestUser";
	String password = "test1234";

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 1000);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		// checks that all users have access to the login page
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testValidUserSignUpAndLoginGetHomePage() {
		// checks if signup page is loading or not
		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		// after signup page loads, register new user
		signup = new SignUpPage(driver);
		signup.signUp(firstname, lastname, username, password);

		// user should be able to login and now view the home page
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		login = new LoginPage(driver);
		login.login(username, password);
		assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testInvalidUserSignUpAndLogin() {
		// checks if signup page is loading or not
		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		// after signup page loads, register new user
		signup = new SignUpPage(driver);
		signup.signUp(firstname, lastname, username, password);

		// user goes to login page, enters invalid username, and gets an error page
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		login = new LoginPage(driver);
		login.login("NotUser", password);
		driver.get("http://localhost:" + this.port + "/login-user");
		assertEquals("Error", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testValidSignUpLoginLogoutAndNoLongerGetsHomePage() {
		// checks if signup page is loading or not
		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		// after signup page loads, register new user
		signup = new SignUpPage(driver);
		signup.signUp(firstname, lastname, username, password);

		// user should be able to login and now view the home page
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		login = new LoginPage(driver);
		login.login(username, password);
		assertEquals("Home", driver.getTitle());

		// checks that user no longer has access to home page once user logs out
		home = new HomePage(driver);
		home.logout();
		wait.until(ExpectedConditions.titleContains("Login"));
		assertEquals("http://localhost:" + this.port + "/login?logout", driver.getCurrentUrl());
		driver.get("http://localhost:" + port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(5)
	public void testAddEditDeleteNotes() throws InterruptedException {
		// checks for signup and login of user
		testValidUserSignUpAndLoginGetHomePage();

		// user can now go to the notes tab, click on button to add a note, enter the info and save changes
		notes = new NotesTabPage(driver);
		Thread.sleep(1000);
		WebElement nav = driver.findElement(By.id("nav-notes-tab"));
		nav.click();
		notes.addNote(driver, "Note title", "Note description", nav);

		// after changes are saved, the result page is shown and user can then view the home page
		driver.get("http://localhost:" + this.port + "/result");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("home-link"))).click();
		driver.get("http://localhost:" + this.port + "/home");

		// checks that user can view an existing note and then edit it
		List<String> detail = notes.getDetail(driver);
		assertEquals("Note title", detail.get(0));
		assertEquals("Note description", detail.get(1));
		notes.editNote(driver, "Edited title", "Edited description");
		driver.get("http://localhost:" + this.port + "/home");
		detail = notes.getDetail(driver);
		assertEquals("Edited title", detail.get(0));
		assertEquals("Edited description", detail.get(1));

		// checks that user can delete an existing note
		notes.deleteNote(driver);
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(driver -> driver.findElement(By.id("nav-notes-tab"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String noteSize = wait.until(driver -> driver.findElement(By.id("note-size")).getText());
		assertEquals("0", noteSize);

		// checks that user no longer has access to home page once user logs out
		home = new HomePage(driver);
		home.logout();
		wait.until(ExpectedConditions.titleIs("Login"));
		assertEquals("http://localhost:" + this.port + "/login?logout", driver.getCurrentUrl());
		driver.get("http://localhost:" + port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(6)
	public void testAddEditDeleteCredentials() throws InterruptedException {
		// checks for signup and login of user
		testValidUserSignUpAndLoginGetHomePage();

		// user can now go to the credentials tab and create a set of credentials
		creds = new CredentialsTabPage(driver);
		Thread.sleep(1000);
		WebElement nav = driver.findElement(By.id("nav-credentials-tab"));
		nav.click();
		creds.addCredential(driver, "credentialUrl.com", "Test Cred Username", "testCred123", nav);

		// after changes are saved, the result page is shown and user can then view the home page
		driver.get("http://localhost:" + this.port + "/result");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("home-link"))).click();
		driver.get("http://localhost:" + this.port + "/home");

		// checks that credentials are displayed and verifies that the displayed password is encrypted
		List<String> detail = creds.getCredDetails(driver);
		assertEquals("credentialUrl.com", detail.get(0));
		assertEquals("Test Cred Username", detail.get(1));
		assertNotEquals("testCred123", detail.get(2));

		// checks that user can edit existing credentials
		creds.editCredential(driver, "editedCredUrl.com", "Edited Test Username", "testCred1234");
		driver.get("http://localhost:" + this.port + "/home");
		detail = creds.getCredDetails(driver);
		assertEquals("editedCredUrl.com", detail.get(0));
		assertEquals("Edited Test Username", detail.get(1));
		assertNotEquals("testCred1234", detail.get(2));

		// checks that user can delete existing credentials
		creds.deleteCredential(driver);
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(driver -> driver.findElement(By.id("nav-credentials-tab"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String credSize = wait.until(driver -> driver.findElement(By.id("credential-size")).getText());
		assertEquals("0", credSize);

		// checks that user no longer has access to home page once user logs out
		home = new HomePage(driver);
		home.logout();
		wait.until(ExpectedConditions.titleIs("Login"));
		assertEquals("http://localhost:" + this.port + "/login?logout", driver.getCurrentUrl());
		driver.get("http://localhost:" + port + "/home");
		assertEquals("Login", driver.getTitle());
	}
}
