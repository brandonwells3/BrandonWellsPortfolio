package utilities;

import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {
	
	Actions action;
	WebDriverWait wait;
	Select select;
	
	public void actionsSendKeys(WebElement element, String text) {
		action = new Actions(Driver.getDriver());
		action.sendKeys(element, text).build().perform();
	}
	public void waitUntilElementVisible(WebElement element) {
		wait = new WebDriverWait(Driver.getDriver(), 5);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	/*public void waitUntilElementNotVisible(WebElement element) {
		wait = new WebDriverWait(Driver.getDriver(), 10);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}*/
	public void waitUntilElementToBeClickable(WebElement element) {
		wait = new WebDriverWait(Driver.getDriver(), 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void selectByVisibleText(WebElement selectElement, String toSelect) {
		select = new Select(selectElement);
		select.selectByVisibleText(toSelect);
	}
	public String getSelectedOption(WebElement selectElement) {
		select = new Select(selectElement);
		String option = select.getFirstSelectedOption().getText();
		return option;
	}
	public int randomNumber() {
		Random random = new Random();
		int randomNum = random.nextInt((999-100) + 1) + 100;
		return randomNum;
	}
	public void actionClick(WebElement element) {
		action = new Actions(Driver.getDriver());
		action.click(element).build().perform();;
	}
	public boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
		return true;
	}
}
