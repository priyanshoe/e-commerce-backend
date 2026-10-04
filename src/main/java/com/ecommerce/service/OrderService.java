package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.OrderItemResponseDto;
import com.ecommerce.Dto.OrderRequestDto;
import com.ecommerce.Dto.OrderResponseDto;
import com.ecommerce.Dto.OrderSellerResponseDto;
import com.ecommerce.Enum.OrderStatus;
import com.ecommerce.entity.Business;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.AccessDeniedException;
import com.ecommerce.exception.BadRequest;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.BusinessRepository;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final OrderItemRepository orderItemRepo;
	private final UserRepository userRepo;
	private final CartItemRepository cartRepo;
	private final BusinessRepository businessRepo;
	
	

	public ResponseEntity<ApiResponseDto> getOrderById(Long id){
		
		Order order = orderRepo.findById(id).orElseThrow(()-> new NotFound("order"));
		OrderResponseDto orderDto = new OrderResponseDto(order);
		List<OrderItem> orderItems = order.getOrderItems();
		ArrayList<OrderItemResponseDto> orderItemDtoList = new ArrayList<OrderItemResponseDto>();
		for(OrderItem orderItem:orderItems) {
			OrderItemResponseDto orderItemDto  = new OrderItemResponseDto(orderItem);
			orderItemDtoList.add(orderItemDto);
		}
		orderDto.setOrderItems(orderItemDtoList);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"order fetched",orderDto));
	}
	

	public ResponseEntity<ApiResponseDto> getMyOrders(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User user = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		List<Order> orders = orderRepo.findAllByCustomer(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"user orders fetched",orders));
	}
	

	public ResponseEntity<ApiResponseDto> getSellerOrders(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User user = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		List<Business> business = businessRepo.findBySeller(user);
		
		ArrayList<OrderSellerResponseDto> orderDtoList = new ArrayList<OrderSellerResponseDto>();
		Set<Long> orderIds = new HashSet<Long>();
		for(Business item: business) { // for each business
			
			List<Order> orders = orderRepo.findOrdersByBusinessId(item.getId());
			for(Order order:orders) { // for each order
				OrderSellerResponseDto orderDto = new OrderSellerResponseDto(order);
				if(!orderIds.add(order.getId())) {// prevent duplicate data
					continue;
				}
				List<OrderItem> orderItems = orderItemRepo.findByOrder(order); 
				ArrayList<OrderItemResponseDto> orderItemDtoList = new ArrayList<OrderItemResponseDto>();
				for(OrderItem orderItem:orderItems) { // for each order Item
					if(!orderItem.getProduct().getBusiness().equals(item)) { // filter belongings items
						continue;
					}
					OrderItemResponseDto orderItemDto = new OrderItemResponseDto(orderItem);
					orderItemDtoList.add(orderItemDto);					
				}
				orderDto.setOrderItems(orderItemDtoList);
				orderDtoList.add(orderDto);
			}
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"seller orders fetched",orderDtoList));
	}

	
	@Transactional
	public ResponseEntity<ApiResponseDto> createOrders(OrderRequestDto orderDto){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setShippingAddress(orderDto.getShippingAddress());
		newOrder.setPaymentMethod(orderDto.getPaymentMethod());
		newOrder.setStatus(OrderStatus.PLACED);
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<CartItem> cartItems = cartRepo.findAllByCustomer(customer);
		if(cartItems.isEmpty()) {
			throw new BadRequest("no items in cart");
		}
		for(CartItem cartItem:cartItems) {
			if(cartItem.getQuantity()> cartItem.getProduct().getStock()) {
				throw new BadRequest("insuffessient stock for product "+cartItem.getProduct().getName());
			}
		}
		ArrayList<OrderItemResponseDto> orderItemDtoList = new ArrayList<OrderItemResponseDto>();
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
			product.setStock(product.getStock() - cartItem.getQuantity());
			
			newOrder.getOrderItems().add(orderItem);
			OrderItemResponseDto orderItemRes = new OrderItemResponseDto(orderItem);
			orderItemDtoList.add(orderItemRes);
			
		}
		newOrder.setTotalAmount(totalAmount);
		Order savedOrder = orderRepo.save(newOrder);
		OrderResponseDto orderResDto = new OrderResponseDto(savedOrder);
		orderResDto.setOrderItems(orderItemDtoList);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"order created",orderResDto));
	}
	
	

	public ResponseEntity<ApiResponseDto> updateOrderStatus(Long id, OrderStatus status){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user"));
		
		List<Business> business = businessRepo.findBySeller(seller);
		Set<Long> buzIds = business.stream().map(Business::getId)
				.collect(Collectors.toSet());
		
		Order existingOrder = orderRepo.findById(id).orElseThrow(()-> new NotFound("order with id: "+id));
		
		boolean isSellerOrder = existingOrder.getOrderItems().stream()
			.anyMatch(item-> buzIds.contains(item.getProduct().getBusiness().getId()));
		if(!isSellerOrder) {
			throw new AccessDeniedException("you don't own this order");
		}
		
		existingOrder.setStatus(status);
		orderRepo.save(existingOrder);
		
		OrderResponseDto response = new OrderResponseDto(existingOrder);
		ArrayList<OrderItemResponseDto> orderItemDto = existingOrder.getOrderItems().stream()
				.map(OrderItemResponseDto::new)
				.collect(Collectors.toCollection(ArrayList::new));
		response.setOrderItems(orderItemDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"order status updated",response));
	}
	
	
	
}









