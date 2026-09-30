package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.CartItemRequestDto;
import com.ecommerce.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartItemController {
	
	private final CartItemService cartItemService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getMyCartItems(){
		return cartItemService.getMyCart();
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDto> addCartItem(@RequestBody CartItemRequestDto cartItem){
		return cartItemService.saveCartItem(cartItem.getProductId(),cartItem.getQuantity());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ApiResponseDto> addCartItem(@PathVariable Long id){
		return cartItemService.deleteCartItem(id);
	}
	
}
