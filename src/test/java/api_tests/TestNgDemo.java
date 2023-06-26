package api_tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.SauceDemoPage;
import utilities.Driver;

public class TestNgDemo {
	
	SauceDemoPage page;
	
	@BeforeTest
	public void beforeTets() {
		System.out.println("BEFORE TEST METHOD");
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("AFTER TEST METHOD");
	}
	
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("BEFORE METHOD");
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("AFTER METHOD");
	}
	
	@Test (priority=3, description="Hard Assert Test", groups={"smoketest", "regression"})
	public void assertionDemo() {
		Assert.assertEquals(true, true);
		System.out.println("HARD ASSERTION, if condition fails, test will stop executing");
	}
	
	@Test (priority=2, description="Soft Assert Test", dependsOnMethods="assertionDemo")
	public void softAssertDemo() {
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(true, true);
		System.out.println("SOFT ASSERTION, if condition fails, test won't stop executing");
		soft.assertAll();
	}
	
	@Test (priority=1, description="UI Test", groups="smoketest")
	public void uiTestDemo() {
		page = new SauceDemoPage();
		Driver.getDriver().get("https://saucedemo.com");
		page.username.sendKeys("standard_user");
		page.password.sendKeys("secret_sauce");
		page.loginBtn.click();
		Assert.assertEquals(page.inventoryItems.size(), 6);
	}
}
