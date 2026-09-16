package com.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserNotFound;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepo;
	
	public ResponseEntity<ApiResponseDto> getAllUsers(){
		List<UserResponseDto> users = userRepo.findAll().stream().map(UserResponseDto::new).toList();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"users fetched",users));
	}
	
	public ResponseEntity<ApiResponseDto> getUserById(Long id){
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFound(id));
		UserResponseDto response = new UserResponseDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user fetched", response));
	}

	public ResponseEntity<ApiResponseDto> getUserByEmail(String email){
		User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFound(email));
		UserResponseDto response = new UserResponseDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user fetched", response));
	}
	
}










