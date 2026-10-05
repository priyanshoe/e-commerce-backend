package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.AdminDashboardResponseDto;
import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Enum.Role;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.repository.BusinessRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepo;
	private final BusinessRepository businessRepo;
	private final ProductRepository productRepo;
	private final OrderRepository orderRepo;
	
	
	public ResponseEntity<ApiResponseDto> getAdminDashboard(){
		
		AdminDashboardResponseDto adminDto = new AdminDashboardResponseDto();
		
		List<User> users = userRepo.findAll();
		Long customerCount = users.stream().filter(user -> user.getRole().equals(Role.CUSTOMER)).count();
		Long sellerCount = users.stream().filter(user -> user.getRole().equals(Role.SELLER)).count();
		Long businessCount = businessRepo.count();
		Long productCount = productRepo.count();
		Long orderCount = orderRepo.count();
		BigDecimal totalRevenue = orderRepo.getTotalSales();
		
		adminDto.setCustomers(customerCount);
		adminDto.setSellers(sellerCount);
		adminDto.setBusinesses(businessCount);
		adminDto.setProducts(productCount);
		adminDto.setOrders(orderCount);
		adminDto.setTotalRevenue(totalRevenue);
		
		List<Order> orders = orderRepo.findTop10ByOrderByOrderDateDesc();
		ArrayList<HashMap<String, String>> recentOrders = new ArrayList<HashMap<String,String>>();
		for(Order order:orders) {
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("id",order.getId().toString());
			hmap.put("customerId",order.getCustomer().getId().toString());
			hmap.put("orderDate", order.getOrderDate().toString());
			hmap.put("paymentMethod", order.getPaymentMethod().toString());
			hmap.put("totalAmount", order.getTotalAmount().toString());
			hmap.put("status", order.getStatus().toString());
			recentOrders.add(hmap);
		}
		adminDto.setRecentOrders(recentOrders);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"admin dashboard fetched",adminDto));
		
	}
}
