package com.airtel.in;

import java.util.List;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

public class LandingPageAndCart extends CommonUtility {

	//Locators Value
	String cartItem = "//*[@id=\"homefeatured\"]//*[@class=\"right-block\"]//*[@class=\"price product-price\"]";
	String cartHover = "(//*[@id='homefeatured']//*[@class='product-container'])";
    String addToCart= "(//*[@id='homefeatured']//*[@class='button ajax_add_to_cart_button btn btn-default'])";	
    String checkout = "//*[@title='Proceed to checkout']";
    String productName = "(//*[@id='homefeatured']//*[@class='product-name'])"	;
	String productNameCart = "//*[@id=\"cart_summary\"]//*[@class='product-name']";
   
	//Variables
	double[] price;
	int counter=0;
	List<WebElement> el1;
	String matchProduct = "";
	String expectedText="";
	
 	public void addToCartMinPriceItem() throws InterruptedException {
        
			try {
				testlog.log(Status.INFO,"On Landing Page");
				el1 = FindElements("xpath", cartItem);
				price = new double[el1.size()];
				for(WebElement e : el1) {
					price[counter] = Double.parseDouble(e.getText().substring(1));
					counter++;
				}  
				addToCart(findMinPrice(price));
				testlog.log(Status.PASS,"Min Price product match in the cart");
				expectedText = getText("xpath", productNameCart );
				assertEqualText(matchProduct, expectedText , "The Item in the cart Matched");
			    takeScreenShot();
			    testlog.log(Status.PASS,"Cart Screenshot taken successfully");
			}
			catch (Exception t) {
				t.printStackTrace();
			}
		}
	
 	
 	public double findMinPrice(double [] price) {
 	
 		double minPrice=  price[0];
 		for(int i = 0; i<price.length; i++ ) {
 	         if(price[i]<minPrice) {
 	            minPrice = price[i];
 	         }
 	      }
 		return minPrice; 
 	}
 	
 	
 	public void addToCart(double price ) {
 	    int counter=1;
 		try {
 		for(WebElement e : el1) {
		    if(Double.parseDouble(e.getText().substring(1))== price){
		      System.out.println(e.getText());	
		      testlog.log(Status.INFO,"Mouse Over to Min Price Product");
		      moveToElement("xpath", cartHover+"["+counter+"]");
		      testlog.log(Status.INFO,"Get the Product Name");
		      matchProduct = getText("xpath", productName+"["+counter+"]");
		      testlog.log(Status.INFO,"Add in cart with Min Price Product");
		      click("xpath", addToCart+"["+counter+"]");
		      testlog.log(Status.INFO,"Checkout with Min Price Product");
		      click("xpath", checkout);
		     }
		    counter++;
 			}
 		}
 		catch(Exception e) {
 			e.printStackTrace();
 		}
			 
 	}
 	
 	//Add to cart with Discount
 	public void addToCartWithDiscount() {
 		try {
			testlog.log(Status.INFO,"On Landing Page");
			el1 = FindElements("xpath", cartItem);
			price = new double[el1.size()];
			System.out.println(el1.size());
			for(WebElement e : el1) {
				price[counter] = Double.parseDouble(e.getText().substring(1));
				counter++;
			}  
			addToCart(findMinPrice(price));
			testlog.log(Status.PASS,"Min Price product match in the cart");
			expectedText = getText("xpath", productNameCart );
			assertEqualText(matchProduct, expectedText , "The Item in the cart Matched");
		    takeScreenShot();
		    testlog.log(Status.PASS,"Cart Screenshot taken successfully");
		}
		catch (Exception t) {
			t.printStackTrace();
		}
 	}
 	
 	
}