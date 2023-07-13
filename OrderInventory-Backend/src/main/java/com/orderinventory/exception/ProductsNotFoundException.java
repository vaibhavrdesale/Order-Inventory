package com.orderinventory.exception;

public class ProductsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5156340627984700908L;

	public ProductsNotFoundException(String message) {
		super(message);
	}
}
