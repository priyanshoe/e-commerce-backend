package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<ApiResponseDto> getAllProduct(){
		return productService.getAllProducts();
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto> getProduct(@PathVariable Long id){
		return productService.getProductById(id);
	}
	
	
}
