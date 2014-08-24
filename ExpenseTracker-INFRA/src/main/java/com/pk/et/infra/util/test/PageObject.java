package com.pk.et.infra.util.test;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

public abstract class PageObject {
	protected final WebDriver driver;

	protected static Properties pageElementsMappings;

	public PageObject(final WebDriver driver) {
		this.driver = driver;
	}

	public void open() {
		this.driver.get(getPageUrl());
	}

	public String getTitile() {
		return this.driver.getTitle();
	}

	public static void setPageElementsMappings(
			final Properties pageElementsMappings) {
		PageObject.pageElementsMappings = pageElementsMappings;
	}

	public abstract boolean isValid();

	public abstract String getPageUrl();
}
