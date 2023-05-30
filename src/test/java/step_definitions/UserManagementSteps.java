package step_definitions;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.DataReader;
import utilities.Driver;

public class UserManagementSteps {

	LoginPage loginpage = new LoginPage();

	@Given("As a user, I am on the login page")
	public void as_a_user_i_am_on_the_login_page() {
		Driver.getDriver().get(DataReader.getProperty("appURL"));
	}
	@When("I enter a valid username and a valid password")
	public void i_enter_a_valid_username_and_a_valid_password() {
		loginpage.emailField.sendKeys(DataReader.getProperty("username"));
		loginpage.passwordField.sendKeys(DataReader.getProperty("password"));
	}
	@When("I click on the login button")
	public void i_click_on_the_login_button() {
		loginpage.loginBtn.click();
	}
	@Then("I should be on the user profile page")
	public void i_should_be_on_the_user_profile_page() {
		Assert.assertTrue(loginpage.accountSettingsHeader.isDisplayed());
	}

}
