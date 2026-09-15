package com.ecommerce.exception;

public class UserAlreayExist extends RuntimeException	 {
	
	public UserAlreayExist(Long id) {
		super("user with ID: "+id+" already exisist");
	}
	public UserAlreayExist(String email) {
		super("user with email: "+email+" already exisist");
	}
}
