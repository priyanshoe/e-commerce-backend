package com.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.UserRequestDto;
import com.ecommerce.Dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadCredential;
import com.ecommerce.exception.UserAlreayExist;
import com.ecommerce.exception.UserNotFound;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepo;
	
	// register
	public ResponseEntity<ApiResponseDto> registerUser(UserRequestDto newUser){
		// check user already exist
		if(userRepo.findByEmail(newUser.getEmail()).isPresent()) {
			throw new UserAlreayExist(newUser.getEmail());
		}
		// check Role should not be == ADMIN
		
		User user = new User(newUser);
		User savedUser = userRepo.save(user);
		UserResponseDto response = new UserResponseDto(savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto(true,"user registered", response));
	}
	
	public ResponseEntity<ApiResponseDto> loginUser(UserRequestDto reqUser){
		// check if user exist
		User user = userRepo.findByEmail(reqUser.getEmail()).orElseThrow(() -> new UserNotFound(reqUser.getEmail())); // change exception to BadCredentials
		if(!user.getPassword().equals(reqUser.getPassword())) {
			System.out.println(user.getPassword() + reqUser.getPassword());
			throw new BadCredential();
		}
		UserResponseDto response = new UserResponseDto(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto(true,"user logged In", response));
	}

}
