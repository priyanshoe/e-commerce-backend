package com.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.CartItemResponseDto;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.AccessDeniedException;
import com.ecommerce.exception.BadRequest;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService {
	
	private final CartItemRepository cartItemRepo;
	private final UserRepository userRepo;
	private final ProductRepository productRepo;
	
	
	public ResponseEntity<ApiResponseDto> getMyCart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user")); 
		List<CartItem> cartItems = cartItemRepo.findAllByCustomer(customer);
		List<CartItemResponseDto> response = cartItems.stream().map(CartItemResponseDto::new).toList();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"cart items fetched",response));
	}
	


	public ResponseEntity<ApiResponseDto> saveCartItem(Long productId, int quantity){
		if(quantity<=0) {
			throw new BadRequest("quntity should greater than zero");
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("user")); 
		Product product = productRepo.findById(productId).orElseThrow(()-> new NotFound("product with id: "+productId));
		
		CartItem newCartItem = new CartItem(customer,product,quantity);		
		CartItem cartItem = cartItemRepo.save(newCartItem);
				
		CartItemResponseDto response = new CartItemResponseDto(cartItem);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"item added to cart",response));
	}
	

	public ResponseEntity<ApiResponseDto> UpdateQuantity(Long id,int quantity){
		if(quantity<0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(false,"quntity should greater than zero",null));
		}
		
		CartItem existingCartItem = cartItemRepo.findById(id).orElseThrow(()-> new NotFound("cart item with id: "+id));
		if(existingCartItem.getQuantity() - quantity < 0) {
			throw new BadRequest("not enough items");
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if (!customer.getId().equals(existingCartItem.getCustomer().getId())) {
		    throw new AccessDeniedException("You don't own this business");
		}

		existingCartItem.setQuantity(quantity);
		CartItem updatedCartItem = cartItemRepo.save(existingCartItem);
		CartItemResponseDto response = new CartItemResponseDto(updatedCartItem);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"cart item quantity updated",response));
	}
	
	

	
	public ResponseEntity<ApiResponseDto> deleteCartItem(Long id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User customer = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		
		CartItem cartItem = cartItemRepo.findById(id).orElseThrow(()-> new NotFound("cart item with id: "+id));
		if (!customer.getId().equals(cartItem.getCustomer().getId())) {
		    throw new AccessDeniedException("You don't own this cart item");
		}
		cartItemRepo.delete(cartItem);;
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"business deleted",null));
	}
}
