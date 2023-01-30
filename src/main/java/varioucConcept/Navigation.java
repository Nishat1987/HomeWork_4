package varioucConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Navigation {
	WebDriver driver;
	String browser;
	String url;

	// Element list
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@name='password']");
	By signinButtonField = By.xpath("//button[@name='login']");
	By Dashboard_Header_Text = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By customerManuField = By.xpath("//span[text()='Customers']");
	By addCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//input[@id='account']");
	By companyDropDownField = By.xpath("//select[@id='cid']");
	By emailField = By.xpath("//*[@id=\"email\"]");
	By phoneNumberField = By.xpath("//*[@id=\"phone\"]");
	By addressField = By.xpath("//input[@id='address']");
	By cityField = By.xpath("//*[@id=\"city\"]");
	By stateField = By.xpath("//*[@id=\"state\"]");
	By ZIPField = By.xpath("//*[@id=\"zip\"]");
	By countryDropDownField = By.xpath("//select[@id='country']");
	By tagField = By.xpath("//*[@id=\"rform\"]/div[1]/div[1]/div[10]/div/span/span[1]/span/ul");
	By itTrainingField = By.xpath("//li[text()='IT Training']");
	By currencyField = By.xpath("//select[@id='currency']");
	By groupField = By.xpath("//select[@id='group']");
	By addCustomerpasswordField = By.xpath("//*[@id=\"password\"]");
	By confirmpasswordField = By.xpath("//*[@id=\"cpassword\"]");
	By welcomeEmailButtonField = By.xpath("//*[@id=\"rform\"]/div[1]/div[2]/div[5]/div/div/div/label[1]");
	By submitButtonField = By.xpath("//*[@id=\"submit\"]");

	// Test data

	String dashboardHeaderText = "Dashboard";

	@BeforeClass
	public void readConfig() {
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used " + browser);
			url = prop.getProperty("url");
		} catch (IOException e) {

		}
	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void loginTest() {
		init();
		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(signinButtonField).click();

		Assert.assertEquals(driver.findElement(Dashboard_Header_Text).getText(), dashboardHeaderText, "wrong page!!!!");

	}

	@Test
	public void customerTest() throws InterruptedException {
		loginTest();
		Thread.sleep(3000);
		driver.findElement(customerManuField).click();
		Thread.sleep(3000);
		driver.findElement(addCustomerField).click();

		Random rnd = new Random();
		int generateNum = rnd.nextInt(999);

		driver.findElement(fullNameField).sendKeys("Nishat sultana" + generateNum);

		selectFromDropDown(driver.findElement(companyDropDownField), "Techfios");

		driver.findElement(emailField).sendKeys(generateNum + "nishat616901@gmail.com");
		driver.findElement(phoneNumberField).sendKeys(generateNum + "5208");
		driver.findElement(addressField).sendKeys("604 Goodwin dr.");
		driver.findElement(cityField).sendKeys("Richardson");
		driver.findElement(stateField).sendKeys("Texas");
		driver.findElement(ZIPField).sendKeys("75081");

		selectFromDropDown(driver.findElement(countryDropDownField), "United States");

		driver.findElement(tagField).click();
		driver.findElement(itTrainingField).click();

		selectFromDropDown(driver.findElement(groupField), "Java");

		driver.findElement(addCustomerpasswordField).sendKeys("654321");
		driver.findElement(confirmpasswordField).sendKeys("654321");
		driver.findElement(welcomeEmailButtonField).click();
		driver.findElement(submitButtonField).click();
	}

	private void selectFromDropDown(WebElement element, String visibleText) {
		Select cf = new Select(element);
		cf.selectByVisibleText(visibleText);

	}
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}