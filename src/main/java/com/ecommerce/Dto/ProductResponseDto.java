package com.ecommerce.Dto;

import java.util.HashMap;

import com.ecommerce.Enum.ProductCategory;
import com.ecommerce.entity.Product;

import lombok.Data;

@Data
public class ProductResponseDto {
	HashMap<String, String> business = new HashMap<String, String>();
	
	private Long id;
	private String name;
	private String description;
	private double price;
	private ProductCategory category;
	private String imageUrl;
	private int stock;
	
	public ProductResponseDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.category = product.getCategory();
		this.imageUrl = product.getImageUrl();
		this.stock = product.getStock();
		
		this.business.put("id", product.getBusiness().getId().toString());
		this.business.put("name", product.getBusiness().getName());
		this.business.put("status", product.getBusiness().getStatus().toString());
		
	}

}
