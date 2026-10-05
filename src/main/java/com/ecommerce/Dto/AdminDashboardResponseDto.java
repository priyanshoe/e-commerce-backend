package com.ecommerce.Dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

@Data
public class AdminDashboardResponseDto {
	
	private Long customers;
	private Long sellers;
	private Long businesses;
	private Long products;
	private Long orders;
	private BigDecimal totalRevenue;
	
	ArrayList<HashMap<String, String>> recentOrders = new ArrayList<HashMap<String, String>>();

}
