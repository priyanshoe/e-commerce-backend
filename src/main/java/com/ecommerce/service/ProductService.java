package com.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.ProductRequestDto;
import com.ecommerce.entity.Business;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.AccessDeniedException;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.BusinessRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository productRepo;
	private final BusinessRepository businessRepo;
	private final UserRepository userRepo;
	

	public ResponseEntity<ApiResponseDto> getAllProducts(){
		List<Product> products = productRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"products fetched",products));
	}
	
	public ResponseEntity<ApiResponseDto> getProductById(Long id){
		Product product = productRepo.findById(id).orElseThrow(()-> new NotFound("product"));
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"product fetched",product));
	}
	
	public ResponseEntity<ApiResponseDto> getProductsByBusiness(Long id){
		Business existingBusiness = businessRepo.findById(id).orElseThrow(()-> new NotFound("business"));
		List<Product> products = productRepo.findByBusiness(existingBusiness);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"products fetched",products));
	}
	
	public ResponseEntity<ApiResponseDto> addProducts(ProductRequestDto newProduct){
		Business existingBusiness = businessRepo.findById(newProduct.getBusinessId()).orElseThrow(()-> new NotFound("business"));
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if(!seller.getId().equals(existingBusiness.getSeller().getId())) {
			throw new AccessDeniedException("You don't own this business");
		}
		
		Product saveProduct = new Product(newProduct);
		saveProduct.setBusiness(existingBusiness);
		Product savedProduct = productRepo.save(saveProduct);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"product added",savedProduct));
	}
	
	public ResponseEntity<ApiResponseDto> updateProducts(Long id, ProductRequestDto newProduct){
		Product existingProduct = productRepo.findById(id).orElseThrow(() -> new NotFound("product with Id: "+id));
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if(!existingProduct.getBusiness().getSeller().getId().equals(seller.getId())) {
			throw new AccessDeniedException("You don't own this product");
		}
		
		existingProduct.setName(newProduct.getName());
		existingProduct.setDescription(newProduct.getDescription());
		existingProduct.setPrice(newProduct.getPrice());
		existingProduct.setCategory(newProduct.getCategory());
		existingProduct.setImageUrl(newProduct.getImageUrl());
		existingProduct.setStock(newProduct.getStock());
		
		Product updatedProduct = productRepo.save(existingProduct);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"product updated",updatedProduct));
	}
	
	public ResponseEntity<ApiResponseDto> deleteProduct(Long id){
		Product existingProduct = productRepo.findById(id).orElseThrow(() -> new NotFound("product with Id: "+id));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if(!existingProduct.getBusiness().getSeller().getId().equals(seller.getId())) {
			throw new AccessDeniedException("You don't own this product");
		}
		
		productRepo.delete(existingProduct);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"products deleted",null));
	}
	
	
	

}
