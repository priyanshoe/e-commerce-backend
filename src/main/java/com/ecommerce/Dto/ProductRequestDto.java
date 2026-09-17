package com.ecommerce.Dto;

import com.ecommerce.Enum.ProductCategory;

import lombok.Data;

@Data
public class ProductRequestDto {
	
	private Long businessId;
	private String name;
	private String description;
	private double price;
	private ProductCategory category;
	private String imageUrl;
	private int stock;

}
