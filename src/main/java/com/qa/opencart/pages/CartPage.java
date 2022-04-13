package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	
	
	private By cart = By.id("cart");
	
	public void addToCart() {
		System.out.println("add to cart is done...");
	}

	public static void main(String[] args) {

		int i = 10;
		System.out.println(i);
		
		String f1 = "feature 1";
		System.out.println(f1);
		
		String f2 = "feature 2";
		System.out.println(f2);

		
	}

}
