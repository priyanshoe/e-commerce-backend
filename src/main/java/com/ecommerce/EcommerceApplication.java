package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;

@RestController
@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
		System.err.println("server running");
	}
	

	@GetMapping({"/","/health", "/status"})
	public ResponseEntity<ApiResponseDto> Server(){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"server is running",null));
	}

}