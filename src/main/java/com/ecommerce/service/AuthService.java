package com.ecommerce.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.AuthResponseDto;
import com.ecommerce.Dto.UserRequestDto;
import com.ecommerce.Dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.AccessDeniedException;
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
		UserResponseDto userResDto = new UserResponseDto(savedUser);
		AuthResponseDto authResDto = new AuthResponseDto(jwtService.genrateToken(savedUser), userResDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user registered", authResDto));
	}
	
	// login
	public ResponseEntity<ApiResponseDto> loginUser(UserRequestDto reqUser){
		try {
			User user = userRepo.findByEmail(reqUser.getEmail()).orElseThrow(()-> new BadCredentialsException(null));
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
			UserResponseDto userResDto = new UserResponseDto(user);
			AuthResponseDto authResDto = new AuthResponseDto(jwtService.genrateToken(user), userResDto);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user logged In", authResDto));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false,"bad credentials", null));
		}
	}
	
	// authMe
	public ResponseEntity<ApiResponseDto> authMe(){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			final String email = authentication.getName();
			User user = userRepo.findByEmail(email).orElseThrow(()-> new AccessDeniedException("user not authenticated"));
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user authenticated", null));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false,ex.getMessage(), null));
		}
	}

}
