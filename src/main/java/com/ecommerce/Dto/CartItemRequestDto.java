package com.ecommerce.Dto;

import lombok.Data;

@Data
public class CartItemRequestDto {
	private Long productId;
	private int quantity;
}
