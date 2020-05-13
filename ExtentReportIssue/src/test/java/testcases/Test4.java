package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import extentreports.config.ExtentService;
import extentreports.config.ExtentTestManager;

public class Test4 {
	
	WebDriver driver;
	
    static {
    	
        System.setProperty("extent.reporter.html.start", "true");
        System.setProperty("extent.reporter.html.config", "./src/main/resources/extentReport/html-config.xml");
        System.setProperty("extent.reporter.html.out", "test-output/ExtentReport/ExtentReport.html");
    }
    
    @BeforeSuite
    public void setUp() {
    	
    	ExtentService.getInstance().setSystemInfo("Dev Name", "Ashwin Pattanayak");
    	ExtentService.getInstance().setSystemInfo("Browser Name", "Chrome");
    	ExtentService.getInstance().setSystemInfo("OS", "WINDOWS 10");

    }
    
    @BeforeMethod
    public void login() {
    	
    	ExtentTestManager.getTest().info("Launching Chrome driver");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/executables/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("Launching Chrome driver");
		
    }
    
    @Test
    public void getTitle() {
    	
    	ExtentTestManager.getTest().info("TestExecuton started.");
    	driver.get("https://www.google.com");
    	String title = driver.getTitle();
    	System.out.println("Title is: "+title);
    }
}
