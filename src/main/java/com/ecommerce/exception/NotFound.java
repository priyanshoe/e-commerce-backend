package com.ecommerce.exception;

public class NotFound extends RuntimeException {

	public NotFound(String item) {
		super(item+" not found");
	}
}
