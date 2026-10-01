package com.ecommerce.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.ecommerce.Enum.OrderStatus;
import com.ecommerce.Enum.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders_data")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User customer;
	
	@OneToMany
	private List<OrderItem> orderItems;
	private BigDecimal totalAmount;
	private PaymentMethod paymentMethod;
	private OrderStatus status;
	
	@CreationTimestamp
	private Timestamp orderDate;
	private String shippingAddress;

}
