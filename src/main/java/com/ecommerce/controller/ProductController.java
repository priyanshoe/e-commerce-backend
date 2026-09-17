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
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<ApiResponseDto> getProducts(){
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto> getProduct(@PathVariable Long id){
		return productService.getProductById(id);
	}
	
	@GetMapping("/business/{id}")
	public ResponseEntity<ApiResponseDto> getBusinessProduct(@PathVariable Long id){
		return productService.getProductsByBusiness(id);
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto> createProduct(@RequestBody ProductRequestDto newProduct){
		return productService.addProducts(newProduct);
	}

	@PutMapping("{id}")
	public ResponseEntity<ApiResponseDto> updateProduct(@PathVariable Long id,@RequestBody ProductRequestDto updateProduct){
		return productService.updateProducts(id, updateProduct);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ApiResponseDto> updateProduct(@PathVariable Long id){
		return productService.deleteProduct(id);
	}
	
}
