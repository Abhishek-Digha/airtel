package com.airtel.in;

import org.testng.annotations.Test;

public class TestLandingPageAndCart extends Setup{

	
	@Test
	public void testLandingPageAndCart() {
	
		TestCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();	
		StartTest(TestCaseName);
		
		LandingPageAndCart obj = new LandingPageAndCart();

		try {
			
			obj.addToCartMinPriceItem();
			
		} catch (Throwable t) {
			System.out.println(t.getLocalizedMessage());
			Error e1 = new Error(t.getMessage());
			e1.setStackTrace(t.getStackTrace());
			throw e1;
		}	
	}	
}
