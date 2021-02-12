package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignUpPage signup;
	private LoginPage login;
	private HomePage home;
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





}
