package step_definitions;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.BrowserUtils;
import utilities.DataReader;
import utilities.Driver;

public class UserManagementSteps {

	LoginPage loginpage = new LoginPage();
	BrowserUtils utils = new BrowserUtils();
	
	String emailAddress;
	String passwordInput;
	
	//VALID LOGIN SCENARIO

	@Given("As a user, I am on the login page")
	public void as_a_user_i_am_on_the_login_page() {
		Driver.getDriver().get(DataReader.getProperty("appURL"));
	}
	@When("I enter a valid username and a valid password")
	public void i_enter_a_valid_username_and_a_valid_password() {
		utils.actionsSendKeys(loginpage.emailField, DataReader.getProperty("username"));
		utils.actionsSendKeys(loginpage.passwordField, DataReader.getProperty("password"));
	}
	@When("I click on the login button")
	public void i_click_on_the_login_button() {
		loginpage.loginBtn.click();
	}
	@Then("I should be on the user profile page")
	public void i_should_be_on_the_user_profile_page() {
		Assert.assertTrue(loginpage.accountSettingsHeader.isDisplayed());
	}
	
	//INVALID USERNAME LOGIN SCENARIO
	
	@When("I enter an invalid username and a valid password")
	public void i_enter_an_invalid_username_and_a_valid_password() {
		utils.actionsSendKeys(loginpage.emailField, "invalid@primetechschool.com");
		utils.actionsSendKeys(loginpage.passwordField, DataReader.getProperty("password"));
	}
	@Then("I should see an error message")
	public void i_should_see_an_error_message() {
		if(emailAddress.equals("") || passwordInput.equals("")) {
			utils.waitUntilElementVisible(loginpage.fieldIsRequiredMessage);
			Assert.assertTrue(loginpage.fieldIsRequiredMessage.isDisplayed());
		} else {
			utils.waitUntilElementVisible(loginpage.invalidLoginErrorMessage);
			Assert.assertTrue(loginpage.invalidLoginErrorMessage.isDisplayed());
		}
	}
	@Then("I should not be logged in")
	public void i_should_not_be_logged_in() {
		Assert.assertTrue(loginpage.emailField.isDisplayed());
	}
	
	//INVALID PASSWORD LOGIN SCENARIO
	
	@When("I enter a valid username and an invalid password")
	public void i_enter_a_valid_username_and_an_invalid_password() {
		utils.actionsSendKeys(loginpage.emailField, DataReader.getProperty("username"));
		utils.actionsSendKeys(loginpage.passwordField, "password123");
	}
	
	//SCENARIO OUTLINE STEPS
	
	@When("I enter an email {string} and password {string}")
	public void i_enter_an_email_and_password(String email, String password) {
		emailAddress = email;
		passwordInput = password;
	    utils.actionsSendKeys(loginpage.emailField, email);
	    utils.actionsSendKeys(loginpage.passwordField, password);
	}
}
