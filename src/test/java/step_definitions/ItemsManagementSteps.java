package step_definitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CraterCommonPage;
import pages.ItemsPage;
import pages.LoginPage;
import utilities.BrowserUtils;
import utilities.DButils;
import utilities.DataReader;
import utilities.Driver;

public class ItemsManagementSteps {
	
	LoginPage loginPage = new LoginPage();
	ItemsPage itemsPage = new ItemsPage();
	CraterCommonPage commonPage = new CraterCommonPage();
	BrowserUtils utils = new BrowserUtils();
	DButils dbUtils = new DButils();
	
	static String itemName;
	static int itemID;
	static List<String> list;
	
	
	@Given("As an entity user, I am logged in")
	public void as_an_entity_user_i_am_logged_in() {
	    Driver.getDriver().get(DataReader.getProperty("appURL"));
	    loginPage.login();
	}
	@Given("I navigate to the Items tab")
	public void i_navigate_to_the_items_tab() {
	    commonPage.itemsLink.click();
	    Assert.assertTrue(itemsPage.itemsPageHeaderText.isDisplayed());
	}
	@When("I click on the Add Item button")
	public void i_click_on_the_add_item_button() {
	    itemsPage.addItemButton.click();
	}
	@Then("I should be on the item input page")
	public void i_should_be_on_the_item_input_page() {
	    Assert.assertTrue(itemsPage.addItemPageHeaderText.isDisplayed());
	}
	@When("I provide item information name {string}, price {int}, unit {string}, and description {string}")
	public void i_provide_item_information_name_price_unit_and_description(String name, Integer price, String unit, String description) {
		itemName = name + utils.randomNumber();
	    utils.actionsSendKeys(itemsPage.addItemName, itemName);
	    utils.actionsSendKeys(itemsPage.addItemPrice, price.toString());
	    itemsPage.addItemUnit.click();
	    utils.waitUntilElementVisible(itemsPage.addItem_pc_Unit);
	    Driver.getDriver().findElement(By.xpath("//span[text()='"+unit+"']")).click();
	    utils.actionsSendKeys(itemsPage.addItemDescription, description);
	}
	@When("I click the Save Item button")
	public void i_click_the_save_item_button() {
	    itemsPage.saveItemButton.click();
	}
	@Then("The item is added to the item list table")
	public void the_item_is_added_to_the_item_list_table() throws InterruptedException {
		if (!utils.isElementPresent(itemsPage.filterNameBox)) {
			utils.waitUntilElementToBeClickable(itemsPage.filterButton);
			utils.actionClick(itemsPage.filterButton);
			utils.waitUntilElementVisible(itemsPage.filterNameBox);
			utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		}
		Thread.sleep(2000);
		Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).isDisplayed());
	}
	
	//UPDATE ITEM SCENARIO STEPS
	
	@When("I select the item {string}")
	public void i_select_the_item(String name) {
		Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).click();
	}
	@When("I should be on the item details page")
	public void i_should_be_on_the_item_details_page() {
		Assert.assertTrue(itemsPage.editItemHeaderText.isDisplayed());
	}
	@When("I update the item price to {int} dollars")
	public void i_update_the_item_price_to_dollars(Integer price) {
	    itemsPage.addItemPrice.clear();
	    utils.actionsSendKeys(itemsPage.addItemPrice, price.toString());
	}
	@When("I click the Update Item button")
	public void i_click_the_update_item_button() {
	    itemsPage.updateButton.click();
	}
	@Then("the Item price is updated to {int} dollars")
	public void the_item_price_is_updated_to_dollars(Integer updatedPrice) {
	    String itemPrice = Driver.getDriver().findElement(By.xpath("(//a[text()='"+itemName+"']//parent::td//following-sibling::td)[2]//span")).getText();
	    System.out.println(itemPrice);
	    String trimmedPrice = itemPrice.substring(2);
	    Assert.assertEquals(trimmedPrice, updatedPrice + ".00");
	}
	
	//DATA TABLE ITEM CREATE STEPS
	
	@When("I provide item information to the fields")
	public void i_provide_item_information_to_the_fields(DataTable dataTable) {
	    List<String> itemInfo = dataTable.asList();
	    for (String info : itemInfo) {
			System.out.println(info);
		}
	    itemName = itemInfo.get(0);
	    utils.actionsSendKeys(itemsPage.addItemName, itemInfo.get(0));
	    utils.actionsSendKeys(itemsPage.addItemPrice, itemInfo.get(1));
	    itemsPage.addItemUnit.click();
	    utils.waitUntilElementVisible(itemsPage.addItem_pc_Unit);
	    Driver.getDriver().findElement(By.xpath("//span[text()='"+itemInfo.get(2)+"']")).click();
	    utils.actionsSendKeys(itemsPage.addItemDescription, itemInfo.get(3));
	}
	
	//ITEM DELETE SCENARIO
	
	@When("I create an item with the following information")
	public void i_create_an_item_with_the_following_information(DataTable dataTable) {
	    list = dataTable.asList();
	    itemName = list.get(0) + utils.randomNumber();
	    itemsPage.createItem(itemName, list.get(1), list.get(2), list.get(3));
	}
	@When("I delete the item created above")
	public void i_delete_the_item_created_above() throws InterruptedException {
	    itemsPage.deleteItem(itemName);
	}
	@Then("The item is no longer in the item list table")
	public void the_item_is_no_longer_in_the_item_list_table() throws InterruptedException {
		Driver.getDriver().navigate().refresh();
		Thread.sleep(2000);
		utils.waitUntilElementToBeClickable(itemsPage.filterButton);
		utils.actionClick(itemsPage.filterButton);
		utils.waitUntilElementVisible(itemsPage.filterNameBox);
		utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		utils.waitUntilElementVisible(itemsPage.filterNoResultFoundMessage);
		Assert.assertTrue(itemsPage.filterNoResultFoundMessage.isDisplayed());
	}
	
	//ITEM CREATE AND CHECK IN DATABASE STEPS
	
	@Then("I should be able to see the item in the database")
	public void i_should_be_able_to_see_the_item_in_the_database() {
		String query = "SELECT name, price, unit_id, description FROM items WHERE name='"+itemName+"';";
		System.out.println(query);
	    List<String> itemInfo = dbUtils.selectArecord(query);
	    for(String string : itemInfo) {
	    	System.out.println(string);
	    }
	    Assert.assertEquals(itemName, itemInfo.get(0));
	    for (int i = 1; i < list.size(); i++) {
	    	if (list.get(i).equals("dz")) {
	    		Assert.assertEquals(itemInfo.get(i), "3");
	    	} else {
	    		Assert.assertEquals(list.get(i), itemInfo.get(i));
	    	}
		}
	}
	
	//UPDATE ITEM AND CHECK IN DATABASE STEPS
	
	@When("I update the item price to {int}")
	public void i_update_the_item_price_to(Integer price) {
	    String updateQuery = "UPDATE items SET price='"+price+"' WHERE name='"+itemName+"';";
	    dbUtils.updateRecord(updateQuery);
	}
	@Then("The item price has been updated to {int} in the database")
	public void the_item_price_has_been_updated_to_in_the_database(Integer price) {
		String query = "SELECT name, price, unit_id, description FROM items WHERE name='"+itemName+"';";
	    List<String> itemInfo = dbUtils.selectArecord(query);
	    System.out.println(itemInfo.get(1));
	    Assert.assertEquals(itemInfo.get(1), price.toString());
	}
	
	//INSERT A RECORD INTO THE DATABASE STEPS
	
	@When("I insert a record into the database called {string}")
	public void i_insert_a_record_into_the_database_called(String name) {
		itemName = name + utils.randomNumber();
		itemID = utils.randomNumber() + 5000;
	    String query = "INSERT INTO items VALUES('"+itemID+"', '"+itemName+"', 'a cool console ', '50000', '1', '11', '2023-06-08 12:53:32', '2023-06-08 12:53:33', '4', '1', '0');";
	    dbUtils.insertRecord(query);
	}
	@Then("The item should be listed on the items table on the UI")
	public void the_item_should_be_listed_on_the_items_table_on_the_ui() throws InterruptedException {
		Driver.getDriver().navigate().refresh();
		utils.waitUntilElementToBeClickable(itemsPage.filterButton);
		utils.actionClick(itemsPage.filterButton);
		utils.waitUntilElementVisible(itemsPage.filterNameBox);
		utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		Thread.sleep(2000);
		Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).isDisplayed());
	}
	
	//DELETE ITEM IN DATABASE STEPS
	
	@When("I delete the item created above via database")
	public void i_delete_the_item_created_above_via_database() {
		String deleteQuery = "DELETE FROM items WHERE id='"+itemID+"';";
	    dbUtils.deleteRecord(deleteQuery);
	}
	@When("I refresh the page")
	public void i_refresh_the_page() {
	    Driver.getDriver().navigate().refresh();
	}
}
