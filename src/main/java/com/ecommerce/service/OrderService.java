package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.OrderRequestDto;
import com.ecommerce.Enum.OrderStatus;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequest;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final UserRepository userRepo;
	private final CartItemRepository cartRepo;
	

	public ResponseEntity<ApiResponseDto> getMyOrders(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User user = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		List<Order> orders = orderRepo.findAllByCustomer(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user orders fetched",orders));
	}
	
	
	public ResponseEntity<ApiResponseDto> createOrders(OrderRequestDto orderDto){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setShippingAddress(orderDto.getAddress());
		newOrder.setPaymentMethod(orderDto.getPaymentMethod());
		newOrder.setStatus(OrderStatus.PLACED);
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<CartItem> cartItems = cartRepo.findAllByCustomer(customer);
		if(cartItems.isEmpty()) {
			throw new BadRequest("no items in cart");
		}
		for(CartItem cartItem:cartItems) {
			Product product = cartItem.getProduct();
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(newOrder);
			orderItem.setProduct(product);
			orderItem.setPrice(product.getPrice());
			orderItem.setQuantity(cartItem.getQuantity());
			BigDecimal subTotal = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
			orderItem.setSubTotal(subTotal);
			totalAmount = totalAmount.add(subTotal);
			
			newOrder.getOrderItems().add(orderItem);
		}
		newOrder.setTotalAmount(totalAmount);
		Order savedOrder = orderRepo.save(newOrder);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"order created",savedOrder));
	}
	
}









