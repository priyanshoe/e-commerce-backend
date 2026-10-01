package com.ecommerce.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_data")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	User customer;
	
	@OneToOne
	@JoinColumn(name = "product_id", nullable = false)
	Product product;

	
	int quantity;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updateAt;
	
	public CartItem(User customer, Product product, int quantity) {
		this.setCustomer(customer);
		this.setProduct(product);
		this.setQuantity(quantity);
	}
	

}
