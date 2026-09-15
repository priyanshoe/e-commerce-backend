package com.ecommerce.exception;

public class UserNotFound extends RuntimeException {
	
	public UserNotFound(Long id) {
		super("user with ID: "+id+" not found");
	}
	
	public UserNotFound(String email) {
		super("user with ID: "+email+" not found");
	}
}
