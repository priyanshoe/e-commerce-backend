package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.Dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto> handleInternalError(Exception ex){
		System.out.println("Internal error: "+ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto(false,"something went wrong, please try again later",null));
	}
	
	// user exceptions
	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ApiResponseDto> handleUserNotFound(UserNotFound ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(false,ex.getMessage(),null));
	}

	@ExceptionHandler(UserAlreayExist.class)
	public ResponseEntity<ApiResponseDto> handleUserNotFound(UserAlreayExist ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto(false,ex.getMessage(),null));
	}

	@ExceptionHandler(BadCredential.class)
	public ResponseEntity<ApiResponseDto> handleBadCredential(BadCredential ex){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false,ex.getMessage(),null));
	}
	
	@ExceptionHandler(NotFound.class)
	public ResponseEntity<ApiResponseDto> handleNotFound(NotFound ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(false,ex.getMessage(),null));
	}
	
	@ExceptionHandler(Conflict.class)
	public ResponseEntity<ApiResponseDto> handleConflict(Conflict ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto(false,ex.getMessage(),null));
	}
}
