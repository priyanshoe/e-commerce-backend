package com.ecommerce.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.Dto.ProductRequestDto;
import com.ecommerce.Enum.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products_data")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "business_id", nullable = false)
	@JsonIgnore
	private Business business;

	private String name;
	private String description;
	private BigDecimal price;
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	private String imageUrl;
	private int stock;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Timestamp updatedAt;
	
	
	public Product(ProductRequestDto newProduct) {
		this.name = newProduct.getName();
		this.description = newProduct.getDescription();
		this.price = newProduct.getPrice();
		this.category = newProduct.getCategory();
		this.imageUrl = newProduct.getImageUrl();
		this.stock = newProduct.getStock();
	}
	

}
