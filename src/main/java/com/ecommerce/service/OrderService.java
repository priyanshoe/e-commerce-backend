package com.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final UserRepository userRepo;
	

	public ResponseEntity<ApiResponseDto> getMyOrders(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User user = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		List<Order> orders = orderRepo.findAllByCustomer(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user orders fetched",orders));
	}
}
