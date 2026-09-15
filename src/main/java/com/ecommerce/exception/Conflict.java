package com.ecommerce.exception;

public class Conflict extends RuntimeException {

	public Conflict(String item) {
		super(item+" not found");
	}
}
