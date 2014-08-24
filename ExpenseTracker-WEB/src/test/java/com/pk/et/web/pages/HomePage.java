package com.pk.et.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pk.et.infra.util.test.PageObject;

public class HomePage extends PageObject {

	public HomePage(final WebDriver driver) {
		super(driver);
	}

	By homeTextLocator = By.xpath("//a[@href='/ExpenseTracker/home']");

	@Override
	public boolean isValid() {
		final WebElement homeLink = this.driver
				.findElement(this.homeTextLocator);
		return homeLink != null && "home".equals(homeLink.getText());
	}

	@Override
	public String getPageUrl() {
		return pageElementsMappings.getProperty("home.url");
	}

}
