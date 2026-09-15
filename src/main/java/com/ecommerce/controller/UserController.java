package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getUsers() {
		return userService.getAllUsers(); 
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponseDto> getUser(@PathVariable Long userId){
		return userService.getUserById(userId);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponseDto> getUser(@PathVariable String email){
		return userService.getUserByEmail(email);
	}
	
}
