package com.airtel.in;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Setup {
	
	
	static WebDriver driver;
	String url = "http://automationpractice.com/";
	ExtentHtmlReporter htmlreporter;
	ExtentReports extent;
    static ExtentTest testlog;
	File extentconfigfile = new File(System.getProperty("user.dir") +"/src/main/java/extent-config.xml");
	String SuiteName;
	String TestCaseName;
	
	@BeforeTest
	public void beforeTest() {
		
		Properties prop = new Properties();
		prop.setProperty("log4j.rootLogger", "WARN");
		PropertyConfigurator.configure(prop);
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/Driver/geckodriver.exe");
	    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.get(url);
		
		
	}
	
	@BeforeClass(alwaysRun = true)
	public void getSuiteName(ITestContext context) {
		SuiteName = context.getCurrentXmlTest().getSuite().getName();
		System.out.println(SuiteName);
	}
	
	@BeforeMethod(alwaysRun = true)
	public ExtentReports ExtentReportConfig() {
		
		if (extent == null) {
	
		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Report/" + SuiteName + ".html");
		htmlreporter.loadXMLConfig(extentconfigfile);
		extent = new ExtentReports();
		
		extent.attachReporter(htmlreporter);
		htmlreporter.setAppendExisting(true);
		// Customize Report property
		extent.setSystemInfo("Host Name", "Pravir Test");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("User Name", "Pravir");
		htmlreporter.config().setDocumentTitle("Test Report");
		htmlreporter.config().setReportName("Shopping Automation");
		htmlreporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlreporter.config().setTheme(Theme.DARK);
		 
	   }
		 
		else {
		
		     htmlreporter.setAppendExisting(true);
		     extent.attachReporter(htmlreporter);   
		    }
		     return extent;
		 }
	
	public void StartTest(String TestcaseName) {
		
		testlog = extent.createTest(TestcaseName);
	}
	
	
	public void logTestFailure() throws IOException {
		
		testlog.log(Status.FAIL, MarkupHelper.createLabel(TestCaseName + " - Test Case Failed", ExtentColor.RED));
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage img = ImageIO.read(screen);
		File filetest = Paths.get(".").toAbsolutePath().normalize().toFile();
		ImageIO.write(img, "png", new File(filetest +File.separator +"Screenshots"+File.separator + TestCaseName +".png"));
	}
	

	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult output) throws Exception {
		if (output.getStatus() == ITestResult.FAILURE) {
			testlog.log(Status.FAIL,MarkupHelper.createLabel(output.getName() + " - Test Case Failed", ExtentColor.RED));
			testlog.log(Status.FAIL,MarkupHelper.createLabel(output.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		} else if (output.getStatus() == ITestResult.SKIP) {
			testlog.log(Status.SKIP,
					MarkupHelper.createLabel(output.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
		else {
			testlog.log(Status.PASS,"Testcase Passed");
		}
		
	}
	
	@AfterClass(alwaysRun = true)
	public void flushReport() {
		extent.flush();
	}
	
	@AfterTest(alwaysRun=true)
	public void  quitDriver() {
		driver.quit();
	}

	

}
