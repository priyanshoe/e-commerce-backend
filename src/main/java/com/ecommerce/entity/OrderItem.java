package com.ecommerce.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items_data")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id",nullable = false)
	@JsonIgnore
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private BigDecimal price; // to prevent price change
	private BigDecimal subTotal;
	private int quantity;

}
