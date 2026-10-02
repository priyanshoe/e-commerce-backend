package com.ecommerce.Dto;

import com.ecommerce.Enum.PaymentMethod;

import lombok.Data;

@Data
public class OrderRequestDto {
	
	private PaymentMethod paymentMethod;
	private String address; 

}
