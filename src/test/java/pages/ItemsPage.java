package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.BrowserUtils;
import utilities.Driver;

public class ItemsPage {
	public ItemsPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	
	BrowserUtils utils = new BrowserUtils();
	
	@FindBy (xpath = "//h3[text()='Items']")
	public WebElement itemsPageHeaderText;
	
	@FindBy (xpath = "//button[text()=' Add Item']")
	public WebElement addItemButton;
	
	@FindBy (xpath = "//h3[text()='New Item']")
	public WebElement addItemPageHeaderText;
	
	@FindBy (xpath = "(//input[@type='text'])[2]")
	public WebElement addItemName;
	
	@FindBy (xpath = "//div[text()='Price ']//parent::label//following-sibling::div/input")
	public WebElement addItemPrice;
	
	@FindBy (xpath = "//div[text()='select unit']//preceding::input[1]")
	public WebElement addItemUnit;
	
	@FindBy (xpath = "//span[text()='pc']")
	public WebElement addItem_pc_Unit;
	
	@FindBy (name = "description")
	public WebElement addItemDescription;
	
	@FindBy (xpath = "//button[text()=' Save Item']")
	public WebElement saveItemButton;
	
	@FindBy (xpath = "//h3[text()='Edit Item']")
	public WebElement editItemHeaderText;
	
	@FindBy (xpath = "//button[text()=' Update Item']")
	public WebElement updateButton;
	
	@FindBy (xpath = "//span[text()='%s'")
	public WebElement unitOptions;
	
	@FindBy (xpath = "//button[text()='Filter ']")
	public WebElement filterButton;
	
	@FindBy (xpath = "//div[@name='name']")
	public WebElement filterNameBox;
	
	@FindBy (xpath = "//a[text()='%s']")
	public WebElement itemNameInTheItemsTable;
	
	@FindBy (xpath = "(//button[contains(@id, 'headlessui')])[3]")
	public WebElement filterItem3dot;
	
	@FindBy (xpath = "//a[text()=' Delete']")
	public WebElement filter3dotDeleteBtn;
	
	@FindBy (xpath = "//button[text()='Ok']")
	public WebElement itemDeleteOkBtn;
	
	@FindBy (xpath = "//span[text()='No Results Found']")
	public WebElement filterNoResultFoundMessage;
	
	@FindBy (xpath = "//p[contains(text(), 'Success!')]")
	public WebElement itemCreateSuccessMessage;
	
	public void createItem(String itemName, String itemPrice, String itemUnit, String itemDescription) {
		addItemName.sendKeys(itemName);
		addItemPrice.sendKeys(itemPrice);
		
		addItemUnit.click();
		utils.waitUntilElementVisible(addItem_pc_Unit);
		Driver.getDriver().findElement(By.xpath("//span[text()='"+itemUnit+"']")).click();
		
		addItemDescription.sendKeys(itemDescription);
		saveItemButton.click();
	}
	
	public void deleteItem(String name) throws InterruptedException {
		Driver.getDriver().navigate().refresh();
		Thread.sleep(9000);
		utils.waitUntilElementToBeClickable(filterButton);
		filterButton.click();
		utils.waitUntilElementVisible(filterNameBox);
		utils.actionsSendKeys(filterNameBox, name);
		Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//a[text()='"+name+"']")).isDisplayed());
		utils.waitUntilElementToBeClickable(filterItem3dot);
		Thread.sleep(2000);
		utils.actionClick(filterItem3dot);
		utils.waitUntilElementToBeClickable(filter3dotDeleteBtn);
		utils.actionClick(filter3dotDeleteBtn);
		utils.waitUntilElementToBeClickable(itemDeleteOkBtn);
		utils.actionClick(itemDeleteOkBtn);
		
	}
}
