package com.qa.woo.ecommerce.exception;

@SuppressWarnings("serial")
public class ProductAlreadyExistsException extends Exception {

	public ProductAlreadyExistsException(String msg) {
		super(msg);
	}
	
}
