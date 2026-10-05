package com.ecommerce.controller;

import com.ecommerce.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.service.BusinessService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	private final UserService userService;
	private final BusinessService businessService;
	private final ProductService productService;
	private final OrderService orderService;
	

	@GetMapping("/dashboard")
	public ResponseEntity<ApiResponseDto> getDashboard(){
		return adminService.getAdminDashboard();
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<ApiResponseDto> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/businesses")
	public ResponseEntity<ApiResponseDto> getAllBusiness(){
		return businessService.getAllBusinesses();
	}
	
	@GetMapping("/products")
	public ResponseEntity<ApiResponseDto> getAllProducts(){
		return productService.getAllProducts();
	}

	@GetMapping("/orders")
	public ResponseEntity<ApiResponseDto> getAllOrders(){
		return orderService.getAllOrders();
	}

}
