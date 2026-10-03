package com.ecommerce.Dto;

import java.math.BigDecimal;
import java.util.HashMap;

import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;

import lombok.Data;

@Data
public class OrderItemResponseDto {
	private Long id;
	private BigDecimal price;
	private int quantity;
	private String name;
	private String imageUrl;
	HashMap<String, String> business = new HashMap<String, String>();
	
	public OrderItemResponseDto(OrderItem orderItem) {
		Product product = orderItem.getProduct();
		this.id = orderItem.getId();
		this.price = orderItem.getPrice();
		this.quantity = orderItem.getQuantity();
		this.name = product.getName();
		this.imageUrl = product.getImageUrl();
		
		this.business.put("id",product.getBusiness().getId().toString());
		this.business.put("name", product.getBusiness().getName());
		this.business.put("status", product.getBusiness().getStatus().toString());
	}
	
}
