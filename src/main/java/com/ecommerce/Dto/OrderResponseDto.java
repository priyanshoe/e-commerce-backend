package com.ecommerce.Dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.ecommerce.Enum.OrderStatus;
import com.ecommerce.Enum.PaymentMethod;
import com.ecommerce.entity.Order;

import lombok.Data;

@Data
public class OrderResponseDto {
	private Long id;
	private String shippingAddress;
	private BigDecimal totalAmount;
	private Timestamp orderDate;
	private PaymentMethod paymentMethod;
	private OrderStatus status;

	ArrayList<OrderItemResponseDto> orderItems = new ArrayList<OrderItemResponseDto>();
	
	
	public OrderResponseDto(Order order) {
		this.id = order.getId();
		this.shippingAddress = order.getShippingAddress();
		this.totalAmount = order.getTotalAmount();
		this.orderDate = order.getOrderDate();
		this.paymentMethod = order.getPaymentMethod();
		this.status = order.getStatus();
		
	}
}
