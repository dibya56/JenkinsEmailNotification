package sample.JenkinsEmailNotification;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class FlipkartTest {

	WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeSuite
	public void setUp() {
		htmlReporter = new ExtentHtmlReporter("test-output/html-reporter/TestExecutionResult.html");
		htmlReporter.config().setDocumentTitle("Test Execution Report");
		htmlReporter.config().setReportName("Flipkart Automation Report");
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Tester Name", "Dibya Saha");

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		driver = new ChromeDriver();
		driver.get("https://www.flipkart.com");
		driver.manage().window().maximize();
	}

	@Test(priority = 0)
	public void loginBtnValidation() {
		try {
			test = extent.createTest("Test 1 - loginBtnValidation");
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_3wFoIb row']")).isDisplayed(),
					"Login Modal Not Working Properly");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z']")).click();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 1)
	public void search() {
		try {
			test = extent.createTest("Test 2 - search");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@name='q']")).click();
			driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Motorola");
			driver.findElement(By.xpath("//button[@class='L0Z3Pu']")).click();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(priority = 2)
	public void cartPageNavigation() {
		try {
			test = extent.createTest("Test 3 - cartPageNavigation");
			driver.findElement(By.xpath("//a[contains(@href, 'viewcart')]")).click();
			Thread.sleep(5000);
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_3jn8H7']")).isDisplayed(),
					"Cart Navigation Not successful");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Failed", ExtentColor.RED));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Skipped", ExtentColor.YELLOW));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Passed", ExtentColor.GREEN));
		}
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
		extent.flush();
	}

}
