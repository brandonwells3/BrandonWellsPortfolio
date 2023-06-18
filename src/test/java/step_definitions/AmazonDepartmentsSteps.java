package step_definitions;

import org.junit.Assert;
import org.openqa.selenium.Keys;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonHomePage;
import utilities.BrowserUtils;
import utilities.Driver;

public class AmazonDepartmentsSteps {
	
	AmazonHomePage homepage = new AmazonHomePage();
	BrowserUtils utils = new BrowserUtils();
	
	@Given("I am on the Amazon home page")
	public void i_am_on_the_amazon_home_page() {
	    Driver.getDriver().get("https://amazon.com");
	    String homepageTitle = Driver.getDriver().getTitle();
	    Assert.assertEquals(homepageTitle, "Amazon.com. Spend less. Smile more.");
	}
	@Given("The departments dropdown is {string}")
	public void the_departments_dropdown_is(String expectedOption) {
		Assert.assertEquals(utils.getSelectedOption(homepage.departmentsDropdown), expectedOption);
	}
	@When("I select the department {string}")
	public void i_select_the_department(String optionToSelect) {
	    utils.selectByVisibleText(homepage.departmentsDropdown, optionToSelect);
	}
	@When("I search {string}")
	public void i_search(String searchItem) {
	    homepage.searchField.sendKeys(searchItem, Keys.ENTER);
	}
	@Then("I should be on {string} search result page")
	public void i_should_be_on_search_result_page(String searchedItem) {
	    String searchPageTitle = Driver.getDriver().getTitle();
	    Assert.assertTrue(searchPageTitle.contains(searchedItem));
	}
}
