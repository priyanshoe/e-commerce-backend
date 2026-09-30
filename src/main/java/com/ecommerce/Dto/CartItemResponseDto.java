package com.ecommerce.Dto;

import com.ecommerce.entity.CartItem;

import lombok.Data;

@Data
public class CartItemResponseDto {
	private Long id;
	private Long customerId;
	private ProductResponseDto product;
	private int quantity;
	
	public CartItemResponseDto(CartItem cartItem) {
		this.setId(cartItem.getId());
		this.setCustomerId(cartItem.getCustomer().getId());
		ProductResponseDto productRes = new ProductResponseDto(cartItem.getProduct());
		this.setProduct(productRes);
		this.setQuantity(cartItem.getQuantity());
	}

}
