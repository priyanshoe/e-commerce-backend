package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.UserRequestDto;
import com.ecommerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponseDto> register(@RequestBody UserRequestDto newUser){
		return authService.registerUser(newUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto> login(@RequestBody UserRequestDto reqUser){
		return authService.loginUser(reqUser);
	}
}
