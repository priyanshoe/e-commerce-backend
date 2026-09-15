package com.ecommerce.exception;

public class BadCredential extends RuntimeException {
	public BadCredential() {
		super(" bad credentials");
	}
}
