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
import com.ecommerce.Dto.ProductRequestDto;
import com.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/business/{buzId}/product")
public class BusinessProductsController {
	
	private final ProductService productService;
	
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getBusinessProduct(@PathVariable Long buzId){
		return productService.getProductsByBusiness(buzId);
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto> createProduct(@PathVariable Long buzId, @RequestBody ProductRequestDto newProduct){
		return productService.addProducts(buzId,newProduct);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto> updateProduct(@PathVariable Long id,@RequestBody ProductRequestDto updateProduct){
		return productService.updateProducts(id, updateProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto> deleteProduct(@PathVariable Long buzId, @PathVariable Long id){
		return productService.deleteProduct(buzId,id);
	}
	
}
