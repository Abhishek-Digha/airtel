package com.airtel.in;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class CommonUtility extends Setup{
	
	//Setup objSetup = new Setup();
	
	public WebElement FindElement(String locater, String value) throws IOException {

		System.out.println("obj val: " + locater + " -> " + value );

		switch (locater) {

			case "id":
				
				return driver.findElement(By.id(value));
	
			case "xpath":
	
				return driver.findElement(By.xpath(value));
	
			case "name":
	
				return driver.findElement(By.name(value));
	
			case "class":
	
				return driver.findElement(By.className(value));
	
			case "tagname":
	
				return driver.findElement(By.tagName(value));
	
			case "css":
	
				return driver.findElement(By.cssSelector(value));
	
			case "linkText":
	
				return driver.findElement(By.linkText(value));
				
			default:
	
				return null;
		}

	}

	
	
	public List<WebElement> FindElements(String locater, String value) throws IOException {

	
		System.out.println("obj val: " + locater + " -> " + value);

		switch (locater) {

			case "id":
				
				return driver.findElements(By.id(value));
	
			case "xpath":
	
				return driver.findElements(By.xpath(value));
	
			case "name":
	
				return driver.findElements(By.name(value));
	
			case "className":
	
				return driver.findElements(By.className(value));
	
			case "tagName":
	
				return driver.findElements(By.tagName(value));
	
			case "cssSelector":
	
				return driver.findElements(By.cssSelector(value));
	
			case "linkText":
	
				return driver.findElements(By.linkText(value));
			default:

				return null;
		}

	}
	
	
	// user defined click Method
	
		public void click(String locater , String value) throws IOException {

			try {
				FindElement(locater, value).click();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}


		// user defined send keys Method
		
		public void sendKeys(String locater, String value, String sendValue) {
			
			try {
				FindElement(locater, value).sendKeys(sendValue);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}


	
		// user defined get text Method
		
		public String getText(String locater, String value) {

			String getValue="";
			
			try {
				 
				getValue = FindElement(locater, value).getText();
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return getValue; 

		}

     
		//User defined get selected method returns True or False
		
		public boolean isSelected(String locater, String value) {

			boolean selectValue=false;
			
			try {
				
				selectValue = FindElement(locater, value).isSelected();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return selectValue;

		}

		
		//Refresh Browser
		
		public void refreshBrowser() {

			driver.navigate().refresh();

		}

		//Get Attribute Value
		
		public String getAttributeValue(String locater, String value) {
				
			String attribValue = "";			
				try {
					attribValue = FindElement(locater, value).getAttribute("value");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return attribValue;
		}
		

		// user defined clear Method
		
		public void clear(String locater, String value) throws IOException {
			
			try {
				
				FindElement(locater, value).clear();
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

		
		//Navigate Back
		
		public void navigateBack() {

			driver.navigate().back();
		}

		
		//Move to Element

		public void moveToElement(String locator, String value) {

			try {
				
				Actions action = new Actions(driver);
				
				action.moveToElement(FindElement(locator, value)).build().perform();
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		
		//Move to Element and Click
		
		public void moveToElementAndClick(String locator, String value) {

			try {
				
				Actions action = new Actions(driver);
				
				action.moveToElement(FindElement(locator, value)).click().build().perform();
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		
		//Move to element Click and Hold
		
		public void moveToElementAndClickHold(String locator, String value) {

			try {
				
				Actions action = new Actions(driver);
				
				action.moveToElement(FindElement(locator, value)).clickAndHold().build().perform();
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		
		//Click first element in the list
		
		public void ClickFirstElementInList(String locater, String value) {
			
			try {
				
				List<WebElement> li = FindElements(locater, value);
				li.get(0).click();
			}
		
		 
		    catch(Exception e) {
		    	e.printStackTrace();
		    }	
		}
		
	
	    public void takeScreenShot() throws IOException{
	    	try {
	    		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				BufferedImage img = ImageIO.read(screen);
				File filetest = Paths.get(".").toAbsolutePath().normalize().toFile();
				ImageIO.write(img, "png", new File(filetest +File.separator +"Screenshots"+File.separator + "CartScreenshot" +".png"));
				testlog.log(Status.PASS, "Cart Screenshot Attched");
				testlog.addScreenCaptureFromPath(System.getProperty("user.dir")+File.separator+"Screenshots" + File.separator + "CartScreenshot" +".png");
				
	    	}
	    	catch(Exception e) {
		    	e.printStackTrace();
		    }
	    }
	

	    public void waitForPageLoaded() throws IOException {
	        ExpectedCondition<Boolean> expectation = new
	                ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver driver) {
	                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
	                    }
	                };
	        try {
	            Thread.sleep(1000);
	            WebDriverWait wait = new WebDriverWait(driver, 70);
	            wait.until(expectation);
	        } catch (Exception e) {
	            System.out.println("Timeout waiting for Page Load Request to complete.");
	            refreshBrowser();
	        }
	    }
	
	    
	    public WebElement WaitUntilPresence(String locator, String value, int TimeinSeconds) throws IOException {

			WebDriverWait wait = new WebDriverWait(driver, TimeinSeconds);
			 
			switch (locator) {

			case "id":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value))));

			case "xpath":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value))));

			case "name":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.name(value))));

			case "class":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.className(value))));

			case "tagName":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(value))));

			case "css":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(value))));

			case "linkText":

				return (wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(value))));
			default:

				return null;
			}
		
		}

	    public void assertEqualText(String Actual, String expected, String message) throws IOException {

	    	try {
	    		Assert.assertEquals(Actual, expected, message);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
		}

}
