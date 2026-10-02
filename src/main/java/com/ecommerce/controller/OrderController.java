package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.OrderRequestDto;
import com.ecommerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto> getUserOrders(){
		return orderService.getMyOrders();
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDto> genrateOrder(@RequestBody OrderRequestDto newOrder){
		return orderService.createOrders(newOrder);
	}

}
