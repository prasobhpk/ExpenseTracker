package com.pk.et.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pk.et.infra.util.test.PageObject;

public class LoginPage extends PageObject {

	private static final String TITLE = "Sign In";

	public LoginPage(final WebDriver driver) {
		super(driver);
		// Check that we're on the right page.
		if (!isValid()) {
			// Alternatively, we could navigate to the login page, perhaps
			// logging out first
			throw new IllegalStateException("This is not the login page");
		}
	}

	// The login page contains several HTML elements that will be represented as
	// WebElements.
	// The locators for these elements should only be defined once.
	By usernameLocator = By.id(pageElementsMappings
			.getProperty("login.locator.username"));
	By passwordLocator = By.id(pageElementsMappings
			.getProperty("login.locator.password"));
	By loginButtonLocator = By.className(pageElementsMappings
			.getProperty("login.locator.loginButton"));
	By loginErrorLocator = By.xpath(pageElementsMappings
			.getProperty("login.locator.error"));

	// The login page allows the user to type their username into the username
	// field
	public LoginPage typeUsername(final String username) {
		// This is the only place that "knows" how to enter a username
		this.driver.findElement(this.usernameLocator).sendKeys(username);

		// Return the current page object as this action doesn't navigate to a
		// page represented by another PageObject
		return this;
	}

	// The login page allows the user to type their password into the password
	// field
	public LoginPage typePassword(final String password) {
		// This is the only place that "knows" how to enter a password
		this.driver.findElement(this.passwordLocator).sendKeys(password);

		// Return the current page object as this action doesn't navigate to a
		// page represented by another PageObject
		return this;
	}

	// The login page allows the user to submit the login form
	public HomePage submitLogin() {
		// This is the only place that submits the login form and expects the
		// destination to be the home page.
		// A seperate method should be created for the instance of clicking
		// login whilst expecting a login failure.
		this.driver.findElement(this.loginButtonLocator).submit();

		// Return a new page object representing the destination. Should the
		// login page ever
		// go somewhere else (for example, a legal disclaimer) then changing the
		// method signature
		// for this method will mean that all tests that rely on this behaviour
		// won't compile.
		return new HomePage(this.driver);

	}

	// The login page allows the user to submit the login form knowing that an
	// invalid username and / or password were entered
	public LoginPage submitLoginExpectingFailure() {
		// This is the only place that submits the login form and expects the
		// destination to be the login page due to login failure.
		this.driver.findElement(this.loginButtonLocator).submit();

		// Return a new page object representing the destination. Should the
		// user ever be navigated to the home page after submiting a login with
		// credentials
		// expected to fail login, the script will fail when it attempts to
		// instantiate the LoginPage PageObject.
		return new LoginPage(this.driver);
	}

	// Conceptually, the login page offers the user the service of being able to
	// "log into"
	// the application using a user name and password.
	public LoginPage typeLoginCredentials(final String username,
			final String password) {
		// The PageObject methods that enter username, password & submit login
		// have already defined and should not be repeated here.
		typeUsername(username);
		typePassword(password);
		return this;
	}

	@Override
	public boolean isValid() {
		return TITLE.equals(this.driver.getTitle());
	}

	public boolean isLoginError() {
		final WebElement errorElement = this.driver
				.findElement(this.loginErrorLocator);
		return errorElement != null
				&& errorElement.getText().contains(
						"Your login attempt was not successful");
	}

	@Override
	public String getPageUrl() {
		return pageElementsMappings.getProperty("login.url");
	}

}
