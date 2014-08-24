package com.pk.et.web.bdd.steps.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.ContextConfiguration;

import com.pk.et.web.pages.HomePage;
import com.pk.et.web.pages.LoginPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration("classpath:config/spring/ExpenseTracker-WEB-context-test.xml")
public class LoginStepDefinitions {

	protected WebDriver driver;
	protected LoginPage loginPage;
	protected HomePage homePage;

	@Before
	public void setUp() {
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		this.loginPage = new LoginPage(this.driver);
		this.loginPage.open();
	}

	@After
	public void tearDown() {
		this.driver.close();
		this.driver.quit();
	}

	@Given("^a User enter user name as \"(.*?)\" and password as \"(.*?)\"$")
	public void a_User_enter_user_name_as_and_password_as(
			final String username, final String password) throws Throwable {
		this.loginPage.typeLoginCredentials(username, password);
	}

	@When("^the user clicks the login button$")
	public void the_user_clicks_the_login_button() throws Throwable {
		this.homePage = this.loginPage.submitLogin();
	}

	@Then("^user should be able to login to the system$")
	public void user_should_be_able_to_login_to_the_system() throws Throwable {
		assertEquals("Proxy", this.driver.getTitle());
		assertTrue(this.homePage.isValid());
	}

	@Then("^an error should be shown$")
	public void an_error_should_be_shown() throws Throwable {
		assertTrue(this.loginPage.isValid());
		assertTrue(this.loginPage.isLoginError());
	}
}
