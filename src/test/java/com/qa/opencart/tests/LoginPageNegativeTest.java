package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100 - Design login page for open cart application")
@Story("US 101 - desgin login page features")
public class LoginPageNegativeTest extends BaseTest{

	@DataProvider
	public Object[][] getLoginNegativeData() {
		return new Object[][] {
			
			{"testwert@gmail.com", "test!!!2233"},
			{"naveenanimation20@gmail.com", "@#@#@#"},
			{"    ", "test@123"},
			{"test@gmail.@.com", "test@121212"},
			{"    ", "   "}
		};
	}
	
	
	@Test(dataProvider = "getLoginNegativeData")
	@Description("login Title Test with invalid credentials.....")
	@Severity(SeverityLevel.NORMAL)
	public void loginInvalidTest(String username, String password) {
		Assert.assertTrue(loginPage.doInvalidLogin(username, password), Errors.LOGIN_PAGE_ERROR_MESSG_NOT_DISPLAYED);
	}
	
//	@Test
//	public void test1() {
//		int a = 10;
//		int b = 20;
//		int sum = a+b;
//		Assert.assertEquals(sum, 40, "...sum is not correct....");
//	}
	
	
	
}
