package com.qa.tracker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import junit.framework.Assert;

public class TrackerPageIT {

	@FindBy(xpath = "/html/body/nav/div/ul/li[2]/a")
	private WebElement Trainers_Button;

	@FindBy(xpath = "/html/body/div/div/div[2]/div/table/tbody/tr[2]/td[2]/button")
	private WebElement John_Button;

	@FindBy(xpath = "/html/body/div/div/div[2]/button")
	private WebElement Hide_Button;

	public void clickHide() {

		Hide_Button.click();
	}

	public void clickPage() {

		Trainers_Button.click();

	}

	public void checkButton() {

		Assert.assertEquals("John Gordon", John_Button.getText());

	}

	public void clickButton() {

		John_Button.click();

	}

}
