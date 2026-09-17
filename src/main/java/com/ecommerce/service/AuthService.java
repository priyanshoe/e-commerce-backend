package com.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.UserRequestDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserAlreayExist;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	// register
	public ResponseEntity<ApiResponseDto> registerUser(UserRequestDto newUser){
		// check user already exist
		if(userRepo.findByEmail(newUser.getEmail()).isPresent()) {
			throw new UserAlreayExist(newUser.getEmail());
		}
		// check Role should not be == ADMIN
		
		User user = new User(newUser);
		user.setPassword(encoder.encode(newUser.getPassword()));
		User savedUser = userRepo.save(user);
//		UserResponseDto response = new UserResponseDto(savedUser);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user registered", jwtService.genrateToken(savedUser)));
	}
	
	public ResponseEntity<ApiResponseDto> loginUser(UserRequestDto reqUser){
		try {
			User user = userRepo.findByEmail(reqUser.getEmail()).orElseThrow(()-> new BadCredentialsException(null));
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user logged In", jwtService.genrateToken(user)));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false,"bad credentials", null));
		}
	}

}
