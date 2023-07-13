package com.orderinventory.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerNotFoundException(String message) {
		super(message);
	}
	public CustomerNotFoundException(HttpStatus notFound, String message) {
		super(message);
	}

}
