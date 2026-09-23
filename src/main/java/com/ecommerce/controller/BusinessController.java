package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.BusinessRequestDto;
import com.ecommerce.service.BusinessService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessController {
	
	private final BusinessService businessService;


	@GetMapping
	public ResponseEntity<ApiResponseDto> getSellerBusiness() {
		return businessService.getMyBusinesses(); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto> getBusinessById(@PathVariable Long id) {
		return businessService.getBusinessById(id); 
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto> createBusiness(@RequestBody BusinessRequestDto newBusiness) {
		return businessService.addBusiness(newBusiness); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto> updateBusiness(@PathVariable Long id, @RequestBody BusinessRequestDto updatedBusiness) {
		return businessService.UpdateBusiness(id, updatedBusiness); 
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto> deleteBusiness(@PathVariable Long id) {
		return businessService.deleteBusiness(id); 
	}
	
	

}
